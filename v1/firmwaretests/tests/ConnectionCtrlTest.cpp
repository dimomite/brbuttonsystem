#include "gmock/gmock.h"
#include "gtest/gtest.h"

extern "C"
{
#include "Comm/ConnectionCtrl.h"
}

#include "../mocks/BtCtrlMock.cpp" // TODO change to .h later if needed
#include "../mocks/RemoteMessagesProcessorMock.cpp"

using ::testing::Eq;

namespace
{
    class ConnectionControllerTest : public ::testing::Test
    {
    protected:
        ConnectionCtrl_t sut;
        testing::StrictMock<BtCtrlMock> btMock;
        testing::StrictMock<RemoteMessagesProcessorMock> messagesProcessorMock;

    public:
        virtual void SetUp()
        {
            EXPECT_CALL(btMock, connect()).Times(1);
            conn_initConnectionCtrl(&sut, btMock.raw(), messagesProcessorMock.raw());
        }

        // virtual void TearDown() {}
    };
} // anon

TEST_F(ConnectionControllerTest, checkInitialization)
{
    ASSERT_NE(nullptr, sut.btCtrl) << "BtCtrl mock is null";
    ASSERT_NE(nullptr, sut.messagesProcessor) << "RemoteMessagesProcessor mock is null";

    ASSERT_EQ(0, sut.missedUsbTicks) << "Missed USB ticks count is not 0";
    ASSERT_EQ(0, sut.missedBtTicks) << "Missed BT ticks count is not 0";
    // sut.status is not tested because that's an "impl details"

    ASSERT_FALSE(conn_isDualCommunicationEnabled(&sut)) << "Duo communication is enabled on start";
}

TEST_F(ConnectionControllerTest, testEnableAndDisableDuoMode)
{
    conn_enableDuoCommunication(&sut);
    ASSERT_TRUE(conn_isDualCommunicationEnabled(&sut)) << "Duo communication is disabled";

    conn_disableDuoCommunication(&sut);
    ASSERT_FALSE(conn_isDualCommunicationEnabled(&sut)) << "Duo communication is enabled";
}

TEST_F(ConnectionControllerTest, testUsbDataHandling)
{
    int8_t data[] = {0, 1, 2, 3, 4};

    // USB is inactive, ticks should be ignored
    conn_onOneSecondTick(&sut);
    ASSERT_EQ(0, sut.missedUsbTicks) << "Wrong number of missed USB ticks";

    EXPECT_CALL(messagesProcessorMock, handleRemoteData(CONN_MSG_USB, data));
    EXPECT_CALL(btMock, disconnect()).Times(1);
    conn_onUsbDataReceived(&sut, data);
    ASSERT_EQ(0, sut.missedUsbTicks) << "Wrong number of missed USB ticks";

    conn_onOneSecondTick(&sut);
    ASSERT_EQ(1, sut.missedUsbTicks) << "Wrong number of missed USB ticks";

    EXPECT_CALL(messagesProcessorMock, handleRemoteData(CONN_MSG_USB, data));
    conn_onUsbDataReceived(&sut, data);
    ASSERT_EQ(0, sut.missedUsbTicks) << "Wrong number of missed USB ticks";
}

TEST_F(ConnectionControllerTest, testBtDataIsIgnoredWhenNotEnabled)
{
    // disable duo communication mode to ensure that BT will be disabled once data over USB is sent
    conn_disableDuoCommunication(&sut);
    ASSERT_FALSE(conn_isDualCommunicationEnabled(&sut)) << "Duo communication is enabled";

    int8_t data[] = {0, 1, 2, 3, 4};
    // send data over USB to disable BT communication
    EXPECT_CALL(btMock, disconnect()).Times(1);
    EXPECT_CALL(messagesProcessorMock, handleRemoteData(CONN_MSG_USB, data));
    conn_onUsbDataReceived(&sut, data);

    ASSERT_EQ(0, sut.missedBtTicks) << "Wrong number of missed BT ticks";

    conn_onBtDataReceived(&sut, data);
    ASSERT_EQ(0, sut.missedBtTicks) << "Wrong number of missed BT ticks";

    // ticks are not updated when BT is disabled
    conn_onOneSecondTick(&sut);
    ASSERT_EQ(0, sut.missedBtTicks) << "Wrong number of missed BT ticks";
}

TEST_F(ConnectionControllerTest, testDuoModeWhileReceivingUsbData)
{
    int8_t usbData[] = {0, 1, 2, 3, 4};
    int8_t btData[] = {10, 11, 12, 13, 14};

    ASSERT_FALSE(conn_isDualCommunicationEnabled(&sut)) << "Duo communication is enabled";
    ASSERT_EQ(0, sut.missedBtTicks) << "Wrong number of missed BT ticks";

    EXPECT_CALL(messagesProcessorMock, handleRemoteData(CONN_MSG_USB, usbData));
    EXPECT_CALL(btMock, disconnect()).Times(1);
    conn_onUsbDataReceived(&sut, usbData);

    conn_onBtDataReceived(&sut, btData);

    EXPECT_CALL(btMock, connect()).Times(1);
    conn_enableDuoCommunication(&sut);

    // BT is Enabled only but not active
    conn_onOneSecondTick(&sut);
    ASSERT_EQ(0, sut.missedBtTicks) << "Wrong number of missed BT ticks";

    // To activate BT - send data
    EXPECT_CALL(messagesProcessorMock, handleRemoteData(CONN_MSG_BT, btData));
    conn_onBtDataReceived(&sut, btData);

    // Once BT is Active it will start counting missed ticks
    conn_onOneSecondTick(&sut);
    ASSERT_EQ(1, sut.missedBtTicks) << "Wrong number of missed BT ticks";
}

TEST_F(ConnectionControllerTest, testUsbGoesInactiveAfterManyTicks)
{
    int8_t usbData[] = {0, 1, 2, 3, 4};

    // move USB to active state
    EXPECT_CALL(btMock, disconnect()).Times(1);
    EXPECT_CALL(messagesProcessorMock, handleRemoteData(CONN_MSG_USB, usbData));
    conn_onUsbDataReceived(&sut, usbData);

    ASSERT_EQ(0, sut.missedUsbTicks) << "Wrong number of missed USB ticks";

    // USB can miss 5 ticks without data update
    for (int i = 0; i < 5; ++i)
    {
        conn_onOneSecondTick(&sut);
    }
    ASSERT_EQ(5, sut.missedUsbTicks) << "Wrong number of missed USB ticks";

    // after one more click USB should go inactive and reenable BT communication
    EXPECT_CALL(btMock, connect()).Times(1);
    conn_onOneSecondTick(&sut);
    ASSERT_EQ(0, sut.missedUsbTicks) << "Wrong number of missed USB ticks";
    ASSERT_EQ(0, sut.missedBtTicks) << "Wrong number of missed BT ticks";
}

TEST_F(ConnectionControllerTest, testBtGoesInactiveAfterManyTicks)
{
    ASSERT_EQ(0, sut.missedBtTicks) << "Wrong number of missed BT ticks";

    // BT is in inactive state, ticks should be ignored
    conn_onOneSecondTick(&sut);
    ASSERT_EQ(0, sut.missedBtTicks) << "Wrong number of missed BT ticks";

    // switch BT to active state by sending data
    int8_t btData[] = {0, 1, 2, 3, 4};
    EXPECT_CALL(messagesProcessorMock, handleRemoteData(CONN_MSG_BT, btData));
    conn_onBtDataReceived(&sut, btData);

    // It's allowed to miss 7 ticks for BT
    for (int i = 0; i < 7; ++i)
    {
        conn_onOneSecondTick(&sut);
    }
    ASSERT_EQ(7, sut.missedBtTicks) << "Wrong number of missed BT ticks";

    // next missed tick disactivates BT, counter should be reset
    conn_onOneSecondTick(&sut);
    ASSERT_EQ(0, sut.missedBtTicks) << "Wrong number of missed BT ticks";
}
