#pragma once

#include <stdint.h>
#include "Comm/RemoteMessagesProcessor.h"
#include "DAL/BtCtrl.h"

typedef struct
{
    uint8_t status;
    BtCtrl_t *btCtrl;
    RemoteMessagesProcessor_t *messagesProcessor;

    uint16_t missedUsbTicks;
    uint16_t missedBtTicks;
} ConnectionCtrl_t;

void conn_initConnectionCtrl(ConnectionCtrl_t *connCtrl, BtCtrl_t *btCtrl, RemoteMessagesProcessor_t *messagesProcessor);

void conn_onOneSecondTick(ConnectionCtrl_t *connCtrl);
void conn_onUsbDataReceived(ConnectionCtrl_t *connCtrl, int8_t *data);
void conn_onBtDataReceived(ConnectionCtrl_t *connCtrl, int8_t *data);

void conn_enableDuoCommunication(ConnectionCtrl_t *connCtrl);
void conn_disableDuoCommunication(ConnectionCtrl_t *connCtrl);
int8_t conn_isDualCommunicationEnabled(ConnectionCtrl_t *connCtrl);
