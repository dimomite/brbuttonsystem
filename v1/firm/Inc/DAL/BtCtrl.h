#pragma once

#include <stdint.h>

typedef enum { 
    BT_CONN_UNDEFINED = 0,
    BT_CONN_NOT_CONNECTED = 1,
    BT_CONN_CONNECTING = 2,
    BT_CONN_CONNECTED = 3,
    BT_CONN_CONNECTION_LOST = 4,
} BtConnectionStatus;

typedef struct {
    /**
     * Header
     */
    int8_t btHeader[2];

    /**
     * ReportId from USB report.
     * Here for compatibility.
     * Not used in BT reports.
     */
    int8_t reportId;

    /**
     * Payload.
     */
    int8_t data[63];

    /**
     * BT report status.
     */
    int8_t btReportStatus;

    /**
     * CRC for BT report.
     */
    int8_t crc[2];
} BtReport_t;


typedef struct {
    void (*onReportReceived)(BtReport_t *report);
    void (*onDataByteReceived)(uint8_t data); // TODO temp, for quick debug only
    void (*onConnectionStatusChanged)(BtConnectionStatus connStatus);
} BtCtrl_t;

void btctrl_onSystemTick(BtCtrl_t *bc);
void btctrl_onDataByteReceived(BtCtrl_t *bc);

void btctrl_connect(BtCtrl_t *bc);
void btctrl_disconnect(BtCtrl_t *bc);

void btctrl_sendReport(BtReport_t *report);
