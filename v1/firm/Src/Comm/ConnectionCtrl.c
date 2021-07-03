#include "Comm/ConnectionCtrl.h"

#define DUO_MODE_ENABLED (1 << 0)
#define USB_STATE_ACTIVE (1 << 1)
#define BT_STATE_ENABLED (1 << 2)
#define BT_STATE_ACTIVE (1 << 3)

// Maximal periods of inactivity of USB and Bluetooth connection.
// That's time withing which Control App should send any data packet.
// And has nothing to do with hardware pings which USB may have.
#define MAX_USB_INACTIVITY (5)
#define MAX_BT_INACTIVITY (7)

#define setStatus(connCtrl, flags) ((connCtrl)->status |= (flags))
#define clearStatus(connCtrl, flags) ((connCtrl)->status &= ~(flags))
#define toggleStatus(connCtrl, flags) ((connCtrl)->status ^= (flags))
#define isSet(connCtrl, flag) ((connCtrl)->status & (flag))

#define isDuoModeEnabled(connCtrl) (isSet(connCtrl, DUO_MODE_ENABLED))
#define isUsbStateActive(connCtrl) (isSet(connCtrl, USB_STATE_ACTIVE))
#define isBtStateEnabled(connCtrl) (isSet(connCtrl, BT_STATE_ENABLED))
#define isBtStateActive(connCtrl) (isSet(connCtrl, BT_STATE_ACTIVE))

static void enableBt(ConnectionCtrl_t *connCtrl)
{
    if (isBtStateEnabled(connCtrl))
        return;

    setStatus(connCtrl, BT_STATE_ENABLED);
    connCtrl->status &= ~BT_STATE_ACTIVE; // TODO maybe not needed, check after writing unit tests
    connCtrl->missedBtTicks = 0;

    btctrl_connect(connCtrl->btCtrl);
}

static void disableBt(ConnectionCtrl_t *connCtrl)
{
    if (!isBtStateEnabled(connCtrl))
        return;

    clearStatus(connCtrl, BT_STATE_ENABLED | BT_STATE_ACTIVE);
    connCtrl->missedBtTicks = 0;
    btctrl_disconnect(connCtrl->btCtrl);
}

void conn_initConnectionCtrl(ConnectionCtrl_t *connCtrl, BtCtrl_t *btCtrl, RemoteMessagesProcessor_t *messagesProcessor)
{
    connCtrl->btCtrl = btCtrl;
    connCtrl->messagesProcessor = messagesProcessor;

    connCtrl->status = 0;
    connCtrl->missedUsbTicks = 0;
    connCtrl->missedBtTicks = 0;

    enableBt(connCtrl);
}

void conn_onOneSecondTick(ConnectionCtrl_t *connCtrl)
{
    if (isUsbStateActive(connCtrl))
    {
        if (++connCtrl->missedUsbTicks > MAX_USB_INACTIVITY)
        {
            clearStatus(connCtrl, USB_STATE_ACTIVE);
            connCtrl->missedUsbTicks = 0;
            enableBt(connCtrl);
        }
    }

    if (isBtStateActive(connCtrl))
    {
        if (++connCtrl->missedBtTicks > MAX_BT_INACTIVITY)
        {
            clearStatus(connCtrl, BT_STATE_ACTIVE);
            connCtrl->missedBtTicks = 0;
        }
    }
}

void conn_onUsbDataReceived(ConnectionCtrl_t *connCtrl, int8_t *data)
{
    connCtrl->missedUsbTicks = 0;
    if (!isUsbStateActive(connCtrl)) // USB was inactive
    {
        setStatus(connCtrl, USB_STATE_ACTIVE);
        if (!isDuoModeEnabled(connCtrl)) // disable BT communication if duo mode is not enabled
        {
            disableBt(connCtrl);
        }
    }

    if (connCtrl->messagesProcessor)
    {
        mess_handleRemoteData(connCtrl->messagesProcessor, CONN_MSG_USB, data);
    }
}

void conn_onBtDataReceived(ConnectionCtrl_t *connCtrl, int8_t *data)
{
    connCtrl->missedBtTicks = 0;
    if (!isBtStateEnabled(connCtrl))
    {
        return;
    }

    if (!isBtStateActive(connCtrl))
    {
        // first message to move from Inactive to Active state
        connCtrl->status |= BT_STATE_ACTIVE;
        connCtrl->missedBtTicks = 0;
    }

    if (connCtrl->messagesProcessor)
    {
        mess_handleRemoteData(connCtrl->messagesProcessor, CONN_MSG_BT, data);
    }
}

void conn_enableDuoCommunication(ConnectionCtrl_t *connCtrl)
{
    if (isDuoModeEnabled(connCtrl))
        return; // duo mode is already enabled

    connCtrl->status |= DUO_MODE_ENABLED;
    enableBt(connCtrl);
}

void conn_disableDuoCommunication(ConnectionCtrl_t *connCtrl)
{
    if (!isDuoModeEnabled(connCtrl))
        return; // duo mode is already disabled

    connCtrl->status &= ~DUO_MODE_ENABLED;
    if (isUsbStateActive(connCtrl))
    {
        disableBt(connCtrl);
    }
}

int8_t conn_isDualCommunicationEnabled(ConnectionCtrl_t *connCtrl)
{
    return isDuoModeEnabled(connCtrl);
}
