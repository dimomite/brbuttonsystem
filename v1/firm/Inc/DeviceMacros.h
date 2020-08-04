/**
 * File collects all static macros to access device hardware.
 * Should help with writing tests when this file can be replaced with mocks.
 */

#pragma once

#include <stm32f1xx_ll_tim.h>
#include <main.h>

#define readMainTimerValue() LL_TIM_GetCounter(TIM3)

#define debugLedOn() LL_GPIO_SetOutputPin(LCD_RESET_GPIO_Port, LCD_RESET_Pin)
#define debugLedOff() LL_GPIO_ResetOutputPin(LCD_RESET_GPIO_Port, LCD_RESET_Pin)

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

#define clearBackLedCsPin() LL_GPIO_ResetOutputPin(BACK_LED_CS_GPIO_Port, BACK_LED_CS_Pin)
#define setBackLedCsPin() LL_GPIO_SetOutputPin(BACK_LED_CS_GPIO_Port, BACK_LED_CS_Pin)

