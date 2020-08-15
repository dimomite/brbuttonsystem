#include "UsbCtrl.h"

#include "DeviceMacros.h"

void usbctrl_onUsbInit(UsbCtrl_t *uc)
{
    if (uc->onConnectionStatusChanged) {
        uc->onConnectionStatusChanged(USB_CONN_CONNECTED);
    }
}

void usbctrl_onUsbDeInit(UsbCtrl_t *uc)
{
    if (uc->onConnectionStatusChanged) {
        uc->onConnectionStatusChanged(USB_CONN_NOT_CONNECTED);
    }
}

void usbctrl_onOutReport(UsbCtrl_t *uc, UsbHidOutReport_t *outReport)
{
    if (uc->onReportReceived) {
        uc->onReportReceived(outReport);
    }
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
    sendUsbReport(inReport, sizeof(UsbHidInReport_t));
}
