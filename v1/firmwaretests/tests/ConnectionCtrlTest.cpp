#include "gmock/gmock.h"
#include "gtest/gtest.h"

extern "C"
{
#include "Comm/ConnectionCtrl.h"
}

#include "../mocks/BtCtrlMock.cpp" // TODO change to .h later

namespace
{
    class ConnectionControllerTest : public ::testing::Test
    {
    protected:
        ConnectionCtrl_t sut;
        testing::StrictMock<BtCtrlMock> btMock;
        // BtCtrlMock btMock;

    public:
        virtual void SetUp()
        {
            conn_initConnectionCtrl(&sut, btMock.raw(), nullptr);
        }

        // virtual void TearDown() {}
    };
} // anon

TEST_F(ConnectionControllerTest, checkInitialization)
{
    ASSERT_NE(nullptr, sut.btCtrl) << "BtCtrl mock is null";
    // ASSERT_NE(nullptr, sut.dataHandler) << "DataHandler mock is null"; // FIXME restore

    ASSERT_EQ(0, sut.missedUsbTicks) << "Missed USB ticks count is not 0";
    ASSERT_EQ(0, sut.missedBtTicks) << "Missed BT ticks count is not 0";
    // sut.status is not tested because that's an "impl details"
}

TEST_F(ConnectionControllerTest, testTimerClicksWithNoOtherCalls)
{
    for (auto i = 0; i < 100; ++i)
    {
        conn_onOneSecondTick(&sut);
    }
}

TEST_F(ConnectionControllerTest, testEnableDuoCommunicationMode)
{
    EXPECT_CALL(btMock, connect()).Times(1);
    conn_enableDuoCommunication(&sut);
}

TEST_F(ConnectionControllerTest, testDisableDuoModeWhenNotEnabledHasNoSideEffect)
{
    conn_disableDuoCommunication(&sut);
}

TEST_F(ConnectionControllerTest, testEnableAndDisableDuoMode)
{
    EXPECT_CALL(btMock, connect()).Times(1);
    conn_enableDuoCommunication(&sut);

    EXPECT_CALL(btMock, disconnect()).Times(1);
    conn_disableDuoCommunication(&sut);
}
