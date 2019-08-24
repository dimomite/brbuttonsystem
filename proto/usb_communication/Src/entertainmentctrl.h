/*
 * entertainmentctrl.h
 *
 *  Created on: Aug 20, 2019
 *      Author: DiMomite
 */

#ifndef ENTERTAINMENTCTRL_H_
#define ENTERTAINMENTCTRL_H_

#include <stdint.h>

typedef enum {
    ENT_SOUND_UNDEFINED = 0,

    ENT_SOUND_START = 1,
    ENT_SOUND_PRESSED = 2,
    ENT_SOUND_FALSE_START = 3
} EntSoundFrequency;

typedef struct {
//    EntSoundFrequency freq;
//    uint8_t startSignalBrightness; // value from 0 to 100
} EntertainmentCtrl;


void ENT_Init(EntertainmentCtrl* entCtrl);

/**
 * @param brightness - value from 0 to 1000
 */
void ENT_StartLedOn(EntertainmentCtrl* entCtrl, uint16_t brightness);
void ENT_StartLedOff(EntertainmentCtrl* entCtrl);

void ENT_EnableSound(EntertainmentCtrl* entCtrl);
void ENT_DisableSound(EntertainmentCtrl* entCtrl);
void ENT_SoundOn(EntertainmentCtrl* entCtrl, EntSoundFrequency freq);
void ENT_SoundOff(EntertainmentCtrl* entCtrl);

#endif /* ENTERTAINMENTCTRL_H_ */
