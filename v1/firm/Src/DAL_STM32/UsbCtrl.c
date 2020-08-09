#include "UsbCtrl.h"

#include "DeviceMacros.h"

void usbctrl_onInit(UsbCtrl_t *uc)
{
    if (uc->onConnectionStatusChanged) {
        uc->onConnectionStatusChanged(USB_CONN_CONNECTED);
    }
}

void usbctrl_onDeInit(UsbCtrl_t *uc)
{
    if (uc->onConnectionStatusChanged) {
        uc->onConnectionStatusChanged(USB_CONN_NOT_CONNECTED);
    }
}

void usbctrl_onOutReport(UsbCtrl_t *uc, UsbHidReport_t *outReport)
{
}

void usbctrl_connect(UsbCtrl_t *uc)
{
    if (uc->onConnectionStatusChanged) {
        uc->onConnectionStatusChanged(USB_CONN_CONNECTING);
    }

    startUsb();
}

void usbctrl_disconnect(UsbCtrl_t *uc)
{
    stopUsb();
}

void usbctrl_sendReport(UsbCtrl_t *uc, UsbHidInReport_t *inReport)
{
}
