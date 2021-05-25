#pragma once

#include <stdint.h>
#include "DAL/BtCtrl.h"

typedef void (RemoteDataHandler)(int8_t *data);

typedef struct
{
    uint8_t status;
    BtCtrl_t *btCtrl;
    RemoteDataHandler *dataHandler;

    uint16_t missedUsbTicks;
    uint16_t missedBtTicks;
} ConnectionCtrl_t;

void conn_initConnectionCtrl(ConnectionCtrl_t *connCtrl, BtCtrl_t *btCtrl, RemoteDataHandler *dataHandler);

void conn_onOneSecondTick(ConnectionCtrl_t *connCtrl);
void conn_onUsbDataReceived(ConnectionCtrl_t *connCtrl, int8_t *data);
void conn_onBtDataReceived(ConnectionCtrl_t *connCtrl, int8_t *data);

void conn_enableDuoCommunication(ConnectionCtrl_t *connCtrl);
void conn_disableDuoCommunication(ConnectionCtrl_t *connCtrl);
