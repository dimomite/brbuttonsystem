/*
 * btctrl.c
 *
 *  Created on: Aug 22, 2019
 *      Author: DiMomite
 */

#include "btctrl.h"
#include "main.h" // TODO remove from this file to platform abstraction

#include "lcdctrl.h" // TODO remove
extern LcdCtrl mainLcdCtrl; // TODO remove

extern UART_HandleTypeDef huart2;

static uint8_t receivingBuffer[1];

void BT_Init(BtCtrl* btctrl) {
    if (!btctrl) return;

    btctrl->state = BT_STATE_UNDEFINED;
}

void BT_EnableBt(BtCtrl* btctrl) {
    if (!btctrl) return;
    if (BT_STATE_ENABLED == btctrl->state) return;

    btctrl->state = BT_STATE_ENABLED;
    HAL_GPIO_WritePin(BT_POWER_GPIO_Port, BT_POWER_Pin, GPIO_PIN_RESET);
    HAL_Delay(100);
    HAL_UART_Receive_IT(&huart2, receivingBuffer, 1);
}

void BT_DisableBt(BtCtrl* btctrl) {
    if (!btctrl) return;
    if (btctrl->state != BT_STATE_ENABLED) return; // further action make sense only for previously enabled controller

    HAL_GPIO_WritePin(BT_POWER_GPIO_Port, BT_POWER_Pin, GPIO_PIN_SET);
    HAL_UART_AbortReceive_IT(&huart2);
}

void BT_SendByte(BtCtrl* btctrl, uint8_t data) {
    if (!btctrl) return;
    if (btctrl->state != BT_STATE_ENABLED) return;

    HAL_UART_Transmit(&huart2, &data, 1, 10);
}

void BT_SendData(BtCtrl* btctrl, uint8_t* data, const uint16_t size) {
    if (!btctrl) return;
    if (!data || !size) return;
    if (btctrl->state != BT_STATE_ENABLED) return;

    HAL_UART_Transmit(&huart2, data, size, 10);
}

void HAL_UART_RxCpltCallback(UART_HandleTypeDef *huart) {
    if (huart != &huart2) return; // not to us

    // TODO do something with data here
    switch (receivingBuffer[0]) {
    case 0: LCD_DrawFilledRectangle(&mainLcdCtrl, 10, 10, 10, 10, LCD_COLOR_WHITE); break;
    case 1: LCD_DrawFilledRectangle(&mainLcdCtrl, 10, 10, 10, 10, LCD_COLOR_MAGENTA); break;
    default: LCD_DrawFilledRectangle(&mainLcdCtrl, 10, 10, 10, 10, LCD_COLOR_CYAN); break;
    }

    // would be nice to check that controller in "enabled" state, but I don't have access to it here

    HAL_UART_Receive_IT(&huart2, receivingBuffer, 1);
}
