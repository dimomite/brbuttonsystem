#include "BtCtrl.h"

#include "DeviceMacros.h"

void btctrl_onSystemTick(BtCtrl_t *bc)
{
}

void btctrl_onDataByteReceived(BtCtrl_t *bc)
{
    if (bc->onDataByteReceived) {
        uint8_t data = getBtData();
        bc->onDataByteReceived(data);
    }
}

void btctrl_connect(BtCtrl_t *bc)
{
    btPowerOn();
    btUsartEnable();
    btUsartEnableRxIt();
}

void btctrl_disconnect(BtCtrl_t *bc)
{
    btUsartDisableRxIt();
    btUsartDisable();
    btPowerOff();
}

void btctrl_sendReport(BtReport_t *report)
{
}
