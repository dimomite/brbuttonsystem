/**
 * File collects all static macros to access device hardware.
 * Should help with writing tests when this file can be replaced with mocks.
 */

#pragma once

#include <stm32f1xx_ll_tim.h>
#include <usbd_customhid.h>
#include <main.h>

extern USBD_HandleTypeDef hUsbDeviceFS;

#define readMainTimerValue() LL_TIM_GetCounter(TIM3)

// #define debugLedOn() LL_GPIO_SetOutputPin(LCD_RESET_GPIO_Port, LCD_RESET_Pin)
// #define debugLedOff() LL_GPIO_ResetOutputPin(LCD_RESET_GPIO_Port, LCD_RESET_Pin)

#define getTouchEnabledPinState() LL_GPIO_IsInputPinSet(TOUCH_ENABLE_GPIO_Port, TOUCH_ENABLE_Pin)

#define readPlayerButtonsPort() LL_GPIO_ReadInputPort(BUTT_0_GPIO_Port)
#define isButton0PinSet() LL_GPIO_IsInputPinSet(BUTT_0_GPIO_Port, BUTT_0_Pin)
#define isButton1PinSet() LL_GPIO_IsInputPinSet(BUTT_1_GPIO_Port, BUTT_1_Pin)
#define isButton2PinSet() LL_GPIO_IsInputPinSet(BUTT_2_GPIO_Port, BUTT_2_Pin)
#define isButton3PinSet() LL_GPIO_IsInputPinSet(BUTT_3_GPIO_Port, BUTT_3_Pin)

#define readButtonsPressedIt() READ_REG(EXTI->PR)
#define resetButtonsPressedIt() WRITE_REG(EXTI->PR, (0xf << 12))
#define disableButtonInterrupts() WRITE_REG(EXTI->IMR, 0)
#define enableButtonInterrupts() WRITE_REG(EXTI->IMR, (0xf << 12))

/**
 * TIM1 for sound and start signals
 */
#define startSound(presc) do { LL_TIM_SetPrescaler(TIM1, (presc)); LL_TIM_CC_EnableChannel(TIM1, LL_TIM_CHANNEL_CH1); } while(0)
#define stopSound() LL_TIM_CC_DisableChannel(TIM1, LL_TIM_CHANNEL_CH1)

#define startLight(intens) do { LL_TIM_OC_SetCompareCH2(TIM1, (intens)); LL_TIM_CC_EnableChannel(TIM1, LL_TIM_CHANNEL_CH2); } while(0)
#define stopLight() LL_TIM_CC_DisableChannel(TIM1, LL_TIM_CHANNEL_CH2)

/**
 * All SPI1 devices.
 */
#define waitSpi1() do { while (LL_SPI_IsActiveFlag_BSY(SPI1)) {}; } while(0)
#define writeSpi1Data(data) do { while (LL_SPI_IsActiveFlag_BSY(SPI1)) {}; LL_SPI_TransmitData8(SPI1, (data)); } while(0)

#define clearFrontLcdCsPin() LL_GPIO_ResetOutputPin(LCD_CS_GPIO_Port, LCD_CS_Pin)
#define setFrontLcdCsPin() LL_GPIO_SetOutputPin(LCD_CS_GPIO_Port, LCD_CS_Pin)

#define clearFrontLcdResetPin() LL_GPIO_ResetOutputPin(LCD_RESET_GPIO_Port, LCD_RESET_Pin)
#define setFrontLcdResetPin() LL_GPIO_SetOutputPin(LCD_RESET_GPIO_Port, LCD_RESET_Pin)

#define clearBackLedCsPin() LL_GPIO_ResetOutputPin(BACK_LED_CS_GPIO_Port, BACK_LED_CS_Pin)
#define setBackLedCsPin() LL_GPIO_SetOutputPin(BACK_LED_CS_GPIO_Port, BACK_LED_CS_Pin)

/**
 * USB control
 */
#define startUsb() LL_GPIO_ResetOutputPin(USB_PULLUP_GPIO_Port, USB_PULLUP_Pin)
#define stopUsb() LL_GPIO_SetOutputPin(USB_PULLUP_GPIO_Port, USB_PULLUP_Pin)
#define sendUsbReport(report, size) USBD_CUSTOM_HID_SendReport(&hUsbDeviceFS, (report), (size))

/**
 * BT module control
 */
#define btUsartEnable() LL_USART_Enable(USART2)
#define btUsartDisable() LL_USART_Disable(USART2)
#define btUsartEnableRxIt() LL_USART_EnableIT_RXNE(USART2)
#define btUsartDisableRxIt() LL_USART_DisableIT_RXNE(USART2)
#define getBtData() LL_USART_ReceiveData8(USART2)
#define btPowerOn() LL_GPIO_ResetOutputPin(BT_POWER_GPIO_Port, BT_POWER_Pin)
#define btPowerOff() LL_GPIO_SetOutputPin(BT_POWER_GPIO_Port, BT_POWER_Pin)


