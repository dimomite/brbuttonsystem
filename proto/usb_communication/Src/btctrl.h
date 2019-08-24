/*
 * btctrl.h
 *
 *  Created on: Aug 22, 2019
 *      Author: DiMomite
 */

#ifndef BTCTRL_H_
#define BTCTRL_H_

#include <stdint.h>

struct BtCtrl;

//typedef void (*OnBtData)(struct BtCtrl*, uint8_t); // TODO Add later to BT_Init as a callback function

typedef enum {
    BT_STATE_UNDEFINED = 0,
    BT_STATE_DISABLED = 1,
    BT_STATE_ENABLED = 2
} BtCtrlState;

typedef struct {
//    OnBtData onData;
    BtCtrlState state;
} BtCtrl;

void BT_Init(BtCtrl* btctrl);
void BT_EnableBt(BtCtrl* btctrl);
void BT_DisableBt(BtCtrl* btctrl);
void BT_SendByte(BtCtrl* btctrl, uint8_t data);
void BT_SendData(BtCtrl* btctrl, uint8_t* data, const uint16_t size);

#endif /* BTCTRL_H_ */
