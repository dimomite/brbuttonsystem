/*
 * entertainmentctrl.c
 *
 *  Created on: Aug 20, 2019
 *      Author: DiMomite
 */

#include "entertainmentctrl.h"

#include "main.h" // TODO temp to get access to HAL structures

#define START_SIGNAL_BRIGHTNESS_MAX (1000)

#define PRESCALER_START_SOUND       (71)
#define PRESCALER_PRESSED_SOUND     (143)
#define PRESCALER_FALSE_START_SOUND (35)

#define SOUND_PWM_CHANNEL           (TIM_CHANNEL_1)
#define START_SIGNAL_PWM_CHANNEL    (TIM_CHANNEL_2)

extern TIM_HandleTypeDef htim1;

static uint32_t freqToPrescaler(const EntSoundFrequency freq);

void ENT_Init(EntertainmentCtrl* entCtrl) {
    if (!entCtrl) return;

    HAL_TIM_PWM_Start(&htim1, START_SIGNAL_PWM_CHANNEL); // need to start timer
}

void ENT_StartLedOn(EntertainmentCtrl* entCtrl, uint16_t brightness) {
    if (!entCtrl) return;

    if (0 == brightness) {
        ENT_StartLedOff(entCtrl);
    } else {
        HAL_TIM_PWM_Start(&htim1, START_SIGNAL_PWM_CHANNEL);
        __HAL_TIM_SET_COMPARE(&htim1, START_SIGNAL_PWM_CHANNEL, brightness);
    }
}

void ENT_StartLedOff(EntertainmentCtrl* entCtrl) {
    if (!entCtrl) return;

//    HAL_GPIO_WritePin(START_LED_GPIO_Port, START_LED_Pin, GPIO_PIN_RESET);
    HAL_TIM_PWM_Stop(&htim1, START_SIGNAL_PWM_CHANNEL);
}

void ENT_EnableSound(EntertainmentCtrl* entCtrl) {
    HAL_GPIO_WritePin(BuzzerEnable_GPIO_Port, BuzzerEnable_Pin, GPIO_PIN_RESET);
}

void ENT_DisableSound(EntertainmentCtrl* entCtrl) {
    HAL_GPIO_WritePin(BuzzerEnable_GPIO_Port, BuzzerEnable_Pin, GPIO_PIN_SET);
}

void ENT_SoundOn(EntertainmentCtrl* entCtrl, EntSoundFrequency freq) {
    if (!entCtrl) return;

    if (ENT_SOUND_UNDEFINED == freq) return; // no sound output, nothing to change // TODO return error code from here
//    if (entCtrl->freq == freq) return; // sound is on and has the same freq - nothing to change

    uint32_t prescaler = freqToPrescaler(freq);
    __HAL_TIM_SET_PRESCALER(&htim1, prescaler);
    HAL_TIM_PWM_Start(&htim1, SOUND_PWM_CHANNEL);
}

void ENT_SoundOff(EntertainmentCtrl* entCtrl) {
    if (!entCtrl) return;

    __HAL_TIM_SET_PRESCALER(&htim1, freqToPrescaler(ENT_SOUND_UNDEFINED)); // switch to 1kHz period for consistency
    HAL_TIM_PWM_Stop(&htim1, SOUND_PWM_CHANNEL);
}

static uint32_t freqToPrescaler(const EntSoundFrequency freq) {
    switch (freq) {
    case ENT_SOUND_START: return PRESCALER_START_SOUND;
    case ENT_SOUND_PRESSED: return PRESCALER_PRESSED_SOUND;
    case ENT_SOUND_FALSE_START: return PRESCALER_FALSE_START_SOUND;
    case ENT_SOUND_UNDEFINED: return PRESCALER_START_SOUND; // Undefined means no sound output but timer can also be used for start signal PWM generation
    }
    return TIM_CLOCKDIVISION_DIV2; // same motivation default definer
}
