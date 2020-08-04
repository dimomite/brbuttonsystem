#pragma once

#include <stdint.h>

typedef enum {
    ENT_FREQ_START = 71,
    ENT_FREQ_PRESSED = 143,
    ENT_FREQ_FALSE_START = 35,
} SoundFreq_t;

typedef struct
{
} EntertainmentCtrl_t;

typedef struct
{
    SoundFreq_t freq;

    /**
     * Duration in ticks (1/128 s). So max duration is approx 2s.
     * If 0 signal will be on until explicitly stopped, but only if repetitions > 0
     */
    uint8_t duration;

    /**
     * Number of repetitions.
     * Signaling and off stages each count as a repetition cycle each.
     * Should be odd number. Even value is senseless because last cycle will be "pause".
     */
    uint8_t repetitions;
} SoundProfile_t;

typedef struct
{
    /**
     * Range [1-999].
     * 0 is off.
     */
    uint16_t intensity;
} LightProfile_t;

void ent_onSystemTick(EntertainmentCtrl_t *ec);

void ent_startSound(EntertainmentCtrl_t *ec, SoundProfile_t const *soundProfile);
void ent_stopSound(EntertainmentCtrl_t *ec);

void ent_startLight(EntertainmentCtrl_t *ec, LightProfile_t const *lightProfile);
void ent_stopLight(EntertainmentCtrl_t *ec);
