#pragma once

#include <stdint.h>

typedef enum {
    USB_CONN_UNDEFINED = 0,
    USB_CONN_NOT_CONNECTED = 1,
    USB_CONN_CONNECTING = 2,
    USB_CONN_CONNECTED = 3,
} UsbConnectionStatus;

typedef struct
{
    uint8_t reportId;
    int8_t data[63];
} UsbHidReport_t;

typedef struct
{
    UsbHidReport_t *payload;
} UsbHidInReport_t;

typedef struct UsbCtrl_t
{
    void (*onReportReceived)(UsbHidReport_t *outReport);
    void (*onConnectionStatusChanged)(UsbConnectionStatus connStatus);
} UsbCtrl_t;

void usbctrl_onInit(UsbCtrl_t *uc);
void usbctrl_onDeInit(UsbCtrl_t *uc);
void usbctrl_onOutReport(UsbCtrl_t *uc, UsbHidReport_t *outReport);

void usbctrl_connect(UsbCtrl_t *uc);
void usbctrl_disconnect(UsbCtrl_t *uc);

void usbctrl_sendReport(UsbCtrl_t *uc, UsbHidInReport_t *inReport);
