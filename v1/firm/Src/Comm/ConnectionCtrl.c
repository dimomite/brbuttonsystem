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

#define isDuoModeEnabled(connCtrl) ((connCtrl)->status & DUO_MODE_ENABLED)
#define isUsbStateActive(connCtrl) ((connCtrl)->status & USB_STATE_ACTIVE)
#define isBtStateEnabled(connCtrl) ((connCtrl)->status & BT_STATE_ENABLED)
#define isBtStateActive(connCtrl) ((connCtrl)->status & BT_STATE_ACTIVE)

static void enableBt(ConnectionCtrl_t *connCtrl)
{
    if (isBtStateEnabled(connCtrl))
        return;

    connCtrl->status |= BT_STATE_ENABLED;
    connCtrl->status &= ~BT_STATE_ACTIVE; // TODO maybe not needed, check after writing unit tests

    btctrl_connect(connCtrl->btCtrl);
}

static void disableBt(ConnectionCtrl_t *connCtrl)
{
    if (!isBtStateEnabled(connCtrl))
        return;

    connCtrl->status &= ~(BT_STATE_ENABLED | BT_STATE_ACTIVE);
    btctrl_disconnect(connCtrl->btCtrl);
}

void conn_initConnectionCtrl(ConnectionCtrl_t *connCtrl, BtCtrl_t *btCtrl, RemoteDataHandler *dataHandler)
{
    connCtrl->btCtrl = btCtrl;
    connCtrl->dataHandler = dataHandler;

    connCtrl->status = 0;
    connCtrl->missedUsbTicks = 0;
    connCtrl->missedBtTicks = 0;
}

void conn_onOneSecondTick(ConnectionCtrl_t *connCtrl)
{
    disableBt(connCtrl);

    if (++connCtrl->missedUsbTicks > MAX_USB_INACTIVITY)
    {
        // TODO
    }

    if (++connCtrl->missedBtTicks > MAX_BT_INACTIVITY)
    {
        conn_disableDuoCommunication(connCtrl);
    }
}

void conn_onUsbDataReceived(ConnectionCtrl_t *connCtrl, int8_t *data)
{
    connCtrl->missedUsbTicks = 0;
    if (!isUsbStateActive(connCtrl)) // USB was inactive
    {
        connCtrl->status |= USB_STATE_ACTIVE;
        if (!isDuoModeEnabled(connCtrl)) // disable BT communication if duo mode is not enabled
        {
            disableBt(connCtrl);
        }
    }

    if (connCtrl->dataHandler)
    {
        connCtrl->dataHandler(data);
    }
}

void conn_onBtDataReceived(ConnectionCtrl_t *connCtrl, int8_t *data)
{
    connCtrl->missedBtTicks = 0;
    if (!isBtStateEnabled(connCtrl))
    {
        return;
    }

    if (connCtrl->dataHandler)
    {
        connCtrl->dataHandler(data);
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
    disableBt(connCtrl);
}
