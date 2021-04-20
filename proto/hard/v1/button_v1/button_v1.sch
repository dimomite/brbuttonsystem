EESchema Schematic File Version 4
EELAYER 30 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 1 4
Title ""
Date ""
Rev ""
Comp ""
Comment1 ""
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
$Comp
L Device:Crystal Y1
U 1 1 5CEC8BEF
P 1250 6550
F 0 "Y1" H 1150 6700 50  0000 C CNN
F 1 "8MHz" H 1350 6700 50  0000 C CNN
F 2 "Crystal:Crystal_SMD_5032-2Pin_5.0x3.2mm" H 1250 6550 50  0001 C CNN
F 3 "~" H 1250 6550 50  0001 C CNN
	1    1250 6550
	1    0    0    -1  
$EndComp
$Comp
L Device:R R4
U 1 1 5CEC9B83
P 1250 6750
F 0 "R4" V 1150 6650 50  0000 C CNN
F 1 "1M" V 1150 6800 50  0000 C CNN
F 2 "Resistor_SMD:R_1206_3216Metric_Pad1.42x1.75mm_HandSolder" V 1180 6750 50  0001 C CNN
F 3 "~" H 1250 6750 50  0001 C CNN
	1    1250 6750
	0    1    -1   0   
$EndComp
Wire Wire Line
	1100 6550 1100 6750
Wire Wire Line
	1400 6550 1400 6750
$Comp
L Device:C C1
U 1 1 5CECBA27
P 1100 7050
F 0 "C1" H 900 7100 50  0000 L CNN
F 1 "22p" H 850 7000 50  0000 L CNN
F 2 "Capacitor_SMD:C_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 1138 6900 50  0001 C CNN
F 3 "~" H 1100 7050 50  0001 C CNN
	1    1100 7050
	1    0    0    -1  
$EndComp
$Comp
L Device:C C2
U 1 1 5CECC3CB
P 1400 7050
F 0 "C2" H 1515 7096 50  0000 L CNN
F 1 "22p" H 1515 7005 50  0000 L CNN
F 2 "Capacitor_SMD:C_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 1438 6900 50  0001 C CNN
F 3 "~" H 1400 7050 50  0001 C CNN
	1    1400 7050
	1    0    0    -1  
$EndComp
Wire Wire Line
	1100 6750 1100 6900
Connection ~ 1100 6750
Wire Wire Line
	1400 6750 1400 6900
Connection ~ 1400 6750
$Comp
L power:GND #PWR06
U 1 1 5CECE2A8
P 1100 7200
F 0 "#PWR06" H 1100 6950 50  0001 C CNN
F 1 "GND" H 1105 7027 50  0001 C CNN
F 2 "" H 1100 7200 50  0001 C CNN
F 3 "" H 1100 7200 50  0001 C CNN
	1    1100 7200
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR07
U 1 1 5CECE6E8
P 1400 7200
F 0 "#PWR07" H 1400 6950 50  0001 C CNN
F 1 "GND" H 1405 7027 50  0001 C CNN
F 2 "" H 1400 7200 50  0001 C CNN
F 3 "" H 1400 7200 50  0001 C CNN
	1    1400 7200
	1    0    0    -1  
$EndComp
Text Label 1400 6550 0    50   ~ 0
OSCIN
Text Label 1100 6550 2    50   ~ 0
OSCOUT
$Comp
L power:+3.3V #PWR040
U 1 1 5D0CBB65
P 5900 950
F 0 "#PWR040" H 5900 800 50  0001 C CNN
F 1 "+3.3V" H 5915 1123 50  0000 C CNN
F 2 "" H 5900 950 50  0001 C CNN
F 3 "" H 5900 950 50  0001 C CNN
	1    5900 950 
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR039
U 1 1 5D0CD5EF
P 5800 1050
F 0 "#PWR039" H 5800 800 50  0001 C CNN
F 1 "GND" H 5805 877 50  0001 C CNN
F 2 "" H 5800 1050 50  0001 C CNN
F 3 "" H 5800 1050 50  0001 C CNN
	1    5800 1050
	0    1    1    0   
$EndComp
Wire Wire Line
	6000 1050 5800 1050
Wire Wire Line
	6000 950  5900 950 
Text Label 6000 1450 2    50   ~ 0
LCD_MOSI
Text Label 6000 1750 2    50   ~ 0
LCD_MISO
Text Label 6000 1550 2    50   ~ 0
LCD_SCK
$Comp
L Transistor_FET:TP0610T Q1
U 1 1 5D147EF5
P 5300 1250
F 0 "Q1" H 5506 1250 50  0000 L CNN
F 1 "TP0610T" H 5506 1205 50  0001 L CNN
F 2 "Package_TO_SOT_SMD:SOT-23" H 5500 1175 50  0001 L CIN
F 3 "http://www.vishay.com/docs/70209/70209.pdf" H 5300 1250 50  0001 L CNN
	1    5300 1250
	1    0    0    1   
$EndComp
Wire Wire Line
	5900 950  5500 950 
Wire Wire Line
	5400 950  5400 1050
Connection ~ 5900 950 
Connection ~ 5400 950 
Wire Wire Line
	5100 1250 5100 950 
Wire Wire Line
	5400 1450 5400 1650
Wire Wire Line
	5400 1650 6000 1650
Text Label 5100 1250 2    50   ~ 0
LCD_LED
Text Label 6000 1250 2    50   ~ 0
LCD_RESET
Text Label 6000 1150 2    50   ~ 0
LCD_CS
Text Label 6000 1350 2    50   ~ 0
LCD_DC
$Comp
L Device:C C16
U 1 1 5D2C1EBC
P 5500 800
F 0 "C16" H 5615 846 50  0000 L CNN
F 1 "0.1uF" H 5615 755 50  0000 L CNN
F 2 "Capacitor_SMD:C_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 5538 650 50  0001 C CNN
F 3 "~" H 5500 800 50  0001 C CNN
	1    5500 800 
	1    0    0    -1  
$EndComp
Connection ~ 5500 950 
Wire Wire Line
	5500 950  5400 950 
$Comp
L power:GND #PWR045
U 1 1 5D2C3838
P 5500 650
F 0 "#PWR045" H 5500 400 50  0001 C CNN
F 1 "GND" H 5505 477 50  0001 C CNN
F 2 "" H 5500 650 50  0001 C CNN
F 3 "" H 5500 650 50  0001 C CNN
	1    5500 650 
	-1   0    0    1   
$EndComp
Wire Wire Line
	3750 4200 3150 4200
Text Label 1850 5100 2    50   ~ 0
TOUCH_SCL
Text Label 1850 5200 2    50   ~ 0
TOUCH_SDA
Text Label 3150 4900 0    50   ~ 0
Sound_PWM
Text Label 1850 4200 2    50   ~ 0
BT_POWER
Text Label 3150 4400 0    50   ~ 0
BTTX_MCURX
Text Label 3150 4300 0    50   ~ 0
BTRX_MCUTX
Text Label 3150 5000 0    50   ~ 0
StartLed
Text Label 3150 5100 0    50   ~ 0
EXT_RW
Text Label 1850 4800 2    50   ~ 0
EXT_READ
Text Label 1850 4700 2    50   ~ 0
EXT_WRITE
$Comp
L power:+3.3V #PWR044
U 1 1 5D2A8270
P 4050 4200
F 0 "#PWR044" H 4050 4050 50  0001 C CNN
F 1 "+3.3V" H 4065 4373 50  0000 C CNN
F 2 "" H 4050 4200 50  0001 C CNN
F 3 "" H 4050 4200 50  0001 C CNN
	1    4050 4200
	0    1    1    0   
$EndComp
$Comp
L Device:R R23
U 1 1 5D2A77C8
P 3900 4200
F 0 "R23" V 3800 4100 50  0000 C CNN
F 1 "100k" V 3800 4300 50  0000 C CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" V 3830 4200 50  0001 C CNN
F 3 "~" H 3900 4200 50  0001 C CNN
	1    3900 4200
	0    1    -1   0   
$EndComp
Text Label 1850 3800 2    50   ~ 0
LCD_CS
Text Label 3150 4200 0    50   ~ 0
LCD_RESET
Text Label 3150 4500 0    50   ~ 0
LCD_DC
Text Label 1850 4100 2    50   ~ 0
LCD_LED
Text Label 3150 4600 0    50   ~ 0
LCD_SCK
Text Label 3150 4700 0    50   ~ 0
LCD_MISO
$Comp
L MCU_ST_STM32F1:STM32F103C8Tx U2
U 1 1 5CEAEC8E
P 2550 4300
F 0 "U2" H 2000 5850 50  0000 C CNN
F 1 "STM32F103C8Tx" H 1950 5750 50  0000 C CNN
F 2 "Package_QFP:LQFP-48_7x7mm_P0.5mm" H 1950 2900 50  0001 R CNN
F 3 "http://www.st.com/st-web-ui/static/active/en/resource/technical/document/datasheet/CD00161566.pdf" H 2550 4300 50  0001 C CNN
	1    2550 4300
	1    0    0    -1  
$EndComp
Text Label 3150 4800 0    50   ~ 0
LCD_MOSI
Text Label 1850 5600 2    50   ~ 0
But3
Text Label 1850 5500 2    50   ~ 0
But2
Text Label 1850 5400 2    50   ~ 0
But1
Text Label 1850 5300 2    50   ~ 0
But0
Wire Wire Line
	1850 3500 1650 3500
Wire Wire Line
	1650 3400 1850 3400
Text Label 1650 3400 2    50   ~ 0
OSCIN
Text Label 1650 3500 2    50   ~ 0
OSCOUT
Wire Wire Line
	3150 5300 3400 5300
Wire Wire Line
	3400 5200 3150 5200
Wire Wire Line
	2550 2700 2550 2600
Wire Wire Line
	2550 2700 2450 2700
Connection ~ 2550 2700
Wire Wire Line
	2550 2800 2550 2700
Wire Wire Line
	2450 2700 2350 2700
Connection ~ 2450 2700
Wire Wire Line
	2450 2800 2450 2700
Wire Wire Line
	2650 2700 2550 2700
Connection ~ 2650 2700
Wire Wire Line
	2650 2800 2650 2700
Wire Wire Line
	2350 2700 2350 2800
Wire Wire Line
	2750 2700 2650 2700
Wire Wire Line
	2750 2800 2750 2700
$Comp
L power:+3.3V #PWR05
U 1 1 5CEB32D8
P 2550 2600
F 0 "#PWR05" H 2550 2450 50  0001 C CNN
F 1 "+3.3V" H 2565 2773 50  0000 C CNN
F 2 "" H 2550 2600 50  0001 C CNN
F 3 "" H 2550 2600 50  0001 C CNN
	1    2550 2600
	1    0    0    -1  
$EndComp
Wire Wire Line
	2500 5900 2550 5900
Connection ~ 2500 5900
Wire Wire Line
	2500 6000 2500 5900
Wire Wire Line
	2450 5900 2500 5900
Connection ~ 2450 5900
Wire Wire Line
	2450 5800 2450 5900
Wire Wire Line
	2550 5900 2650 5900
Connection ~ 2550 5900
Wire Wire Line
	2550 5800 2550 5900
Wire Wire Line
	2650 5900 2650 5800
Wire Wire Line
	2350 5900 2450 5900
Wire Wire Line
	2350 5800 2350 5900
$Comp
L power:GND #PWR04
U 1 1 5CEB1C86
P 2500 6000
F 0 "#PWR04" H 2500 5750 50  0001 C CNN
F 1 "GND" H 2505 5827 50  0001 C CNN
F 2 "" H 2500 6000 50  0001 C CNN
F 3 "" H 2500 6000 50  0001 C CNN
	1    2500 6000
	1    0    0    -1  
$EndComp
Text Label 3150 5400 0    50   ~ 0
SWDIO
Text Label 3150 5500 0    50   ~ 0
SWCLK
Text Label 1850 3200 2    50   ~ 0
BOOT0
Text Label 1850 4300 2    50   ~ 0
BOOT1
Text Label 1850 3700 2    50   ~ 0
TouchEnable
$Comp
L power:GND #PWR036
U 1 1 5D0E3AE6
P 3300 3200
F 0 "#PWR036" H 3300 2950 50  0001 C CNN
F 1 "GND" H 3305 3027 50  0001 C CNN
F 2 "" H 3300 3200 50  0001 C CNN
F 3 "" H 3300 3200 50  0001 C CNN
	1    3300 3200
	1    0    0    -1  
$EndComp
Wire Wire Line
	3750 3200 3300 3200
Wire Wire Line
	3750 3200 4200 3200
Connection ~ 3750 3200
$Comp
L Device:C C14
U 1 1 5D0E1987
P 3300 3050
F 0 "C14" H 3415 3096 50  0000 L CNN
F 1 "0.1uF" H 3415 3005 50  0000 L CNN
F 2 "Capacitor_SMD:C_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 3338 2900 50  0001 C CNN
F 3 "~" H 3300 3050 50  0001 C CNN
	1    3300 3050
	1    0    0    -1  
$EndComp
$Comp
L power:+3.3V #PWR035
U 1 1 5D0E374B
P 3300 2900
F 0 "#PWR035" H 3300 2750 50  0001 C CNN
F 1 "+3.3V" H 3315 3073 50  0000 C CNN
F 2 "" H 3300 2900 50  0001 C CNN
F 3 "" H 3300 2900 50  0001 C CNN
	1    3300 2900
	1    0    0    -1  
$EndComp
$Comp
L Device:C C9
U 1 1 5D0DBD3D
P 3750 3050
F 0 "C9" H 3865 3096 50  0000 L CNN
F 1 "0.1uF" H 3865 3005 50  0000 L CNN
F 2 "Capacitor_SMD:C_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 3788 2900 50  0001 C CNN
F 3 "~" H 3750 3050 50  0001 C CNN
	1    3750 3050
	1    0    0    -1  
$EndComp
Wire Wire Line
	3750 2900 3300 2900
$Comp
L Device:C C24
U 1 1 5D604E60
P 4200 3050
F 0 "C24" H 4315 3096 50  0000 L CNN
F 1 "0.1uF" H 4315 3005 50  0000 L CNN
F 2 "Capacitor_SMD:C_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 4238 2900 50  0001 C CNN
F 3 "~" H 4200 3050 50  0001 C CNN
	1    4200 3050
	1    0    0    -1  
$EndComp
Wire Wire Line
	4200 2900 3750 2900
Connection ~ 3750 2900
$Comp
L Connector:Conn_01x04_Female J7
U 1 1 5D0CCB3B
P 9050 1400
F 0 "J7" H 9078 1330 50  0000 L CNN
F 1 "Conn_01x04_Female" H 9078 1285 50  0001 L CNN
F 2 "Connector_PinHeader_2.54mm:PinHeader_1x04_P2.54mm_Horizontal" H 9050 1400 50  0001 C CNN
F 3 "~" H 9050 1400 50  0001 C CNN
	1    9050 1400
	1    0    0    -1  
$EndComp
Connection ~ 8350 900 
Wire Wire Line
	8450 900  8350 900 
Wire Wire Line
	8050 900  8050 1100
Text Label 8050 1100 2    50   ~ 0
BT_POWER
$Comp
L Device:R R26
U 1 1 5D183812
P 8200 900
F 0 "R26" V 8300 800 50  0000 C CNN
F 1 "47k" V 8300 950 50  0000 C CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" V 8130 900 50  0001 C CNN
F 3 "~" H 8200 900 50  0001 C CNN
	1    8200 900 
	0    1    -1   0   
$EndComp
$Comp
L Transistor_FET:TP0610T Q3
U 1 1 5D16935B
P 8250 1100
F 0 "Q3" H 8456 1100 50  0000 L CNN
F 1 "TP0610T" H 8456 1055 50  0001 L CNN
F 2 "Package_TO_SOT_SMD:SOT-23" H 8450 1025 50  0001 L CIN
F 3 "http://www.vishay.com/docs/70209/70209.pdf" H 8250 1100 50  0001 L CNN
	1    8250 1100
	1    0    0    1   
$EndComp
Wire Wire Line
	8350 1300 7800 1300
Connection ~ 8350 1300
Connection ~ 7800 1600
Wire Wire Line
	8150 1600 7800 1600
Wire Wire Line
	8150 1400 8150 1600
Wire Wire Line
	8850 1400 8150 1400
Wire Wire Line
	8850 1300 8350 1300
$Comp
L Device:C C17
U 1 1 5D0DC7F2
P 7800 1450
F 0 "C17" H 7915 1496 50  0000 L CNN
F 1 "0.1uF" H 7915 1405 50  0000 L CNN
F 2 "Capacitor_SMD:C_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 7838 1300 50  0001 C CNN
F 3 "~" H 7800 1450 50  0001 C CNN
	1    7800 1450
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR048
U 1 1 5D0DB37A
P 7800 1600
F 0 "#PWR048" H 7800 1350 50  0001 C CNN
F 1 "GND" H 7805 1427 50  0001 C CNN
F 2 "" H 7800 1600 50  0001 C CNN
F 3 "" H 7800 1600 50  0001 C CNN
	1    7800 1600
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR0120
U 1 1 5D194DEC
P 8100 3050
F 0 "#PWR0120" H 8100 2800 50  0001 C CNN
F 1 "GND" H 8105 2877 50  0001 C CNN
F 2 "" H 8100 3050 50  0001 C CNN
F 3 "" H 8100 3050 50  0001 C CNN
	1    8100 3050
	0    -1   -1   0   
$EndComp
Text Label 7750 2200 2    50   ~ 0
TouchEnable
$Comp
L power:GND #PWR0113
U 1 1 5D0F6A26
P 8100 2300
F 0 "#PWR0113" H 8100 2050 50  0001 C CNN
F 1 "GND" H 8105 2127 50  0001 C CNN
F 2 "" H 8100 2300 50  0001 C CNN
F 3 "" H 8100 2300 50  0001 C CNN
	1    8100 2300
	0    -1   -1   0   
$EndComp
Text Label 8800 3200 0    50   ~ 0
CX4
Text Label 8800 3100 0    50   ~ 0
CX3
Text Label 8800 3000 0    50   ~ 0
CX2
Text Label 8800 2900 0    50   ~ 0
CX1
Text Label 8800 2800 0    50   ~ 0
CX0
Wire Wire Line
	9400 2300 9400 2500
$Comp
L power:GND #PWR0118
U 1 1 5D50F187
P 9200 2300
F 0 "#PWR0118" H 9200 2050 50  0001 C CNN
F 1 "GND" H 9205 2127 50  0001 C CNN
F 2 "" H 9200 2300 50  0001 C CNN
F 3 "" H 9200 2300 50  0001 C CNN
	1    9200 2300
	-1   0    0    1   
$EndComp
Wire Wire Line
	8950 2700 8950 2600
Wire Wire Line
	9400 2700 8950 2700
Wire Wire Line
	9200 2600 9400 2600
$Comp
L Device:C C25
U 1 1 5D4F02EB
P 8950 2450
F 0 "C25" V 8900 2300 50  0000 L CNN
F 1 "20pF" V 9000 2200 50  0000 L CNN
F 2 "Capacitor_SMD:C_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 8988 2300 50  0001 C CNN
F 3 "~" H 8950 2450 50  0001 C CNN
	1    8950 2450
	-1   0    0    1   
$EndComp
Wire Wire Line
	9000 2800 8800 2800
Wire Wire Line
	9350 2900 9400 2900
Wire Wire Line
	9400 3000 9350 3000
Wire Wire Line
	9350 3100 9400 3100
Wire Wire Line
	9400 3200 9350 3200
Wire Wire Line
	9300 2800 9400 2800
$Comp
L Device:R R34
U 1 1 5D42D65B
P 9150 2800
F 0 "R34" V 9200 2650 50  0000 C CNN
F 1 "3k" V 9100 2600 50  0000 C CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" V 9080 2800 50  0001 C CNN
F 3 "~" H 9150 2800 50  0001 C CNN
	1    9150 2800
	0    1    -1   0   
$EndComp
Wire Wire Line
	10900 2600 10900 2500
Connection ~ 10900 2600
Wire Wire Line
	10900 2700 10900 2600
$Comp
L power:+3.3V #PWR0117
U 1 1 5D41F9A9
P 10900 2500
F 0 "#PWR0117" H 10900 2350 50  0001 C CNN
F 1 "+3.3V" H 10915 2673 50  0000 C CNN
F 2 "" H 10900 2500 50  0001 C CNN
F 3 "" H 10900 2500 50  0001 C CNN
	1    10900 2500
	1    0    0    -1  
$EndComp
Wire Wire Line
	10600 2700 10100 2700
Wire Wire Line
	10100 2600 10600 2600
$Comp
L Device:R R40
U 1 1 5D4020A6
P 10750 2700
F 0 "R40" V 10650 2600 50  0000 C CNN
F 1 "4k7" V 10650 2750 50  0000 C CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" V 10680 2700 50  0001 C CNN
F 3 "~" H 10750 2700 50  0001 C CNN
	1    10750 2700
	0    1    -1   0   
$EndComp
$Comp
L Device:R R39
U 1 1 5D401C9C
P 10750 2600
F 0 "R39" V 10850 2500 50  0000 C CNN
F 1 "4k7" V 10850 2650 50  0000 C CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" V 10680 2600 50  0001 C CNN
F 3 "~" H 10750 2600 50  0001 C CNN
	1    10750 2600
	0    1    -1   0   
$EndComp
Text Label 10100 2600 0    50   ~ 0
TOUCH_SDA
Wire Wire Line
	10150 2500 10350 2500
Text Label 10100 2700 0    50   ~ 0
TOUCH_SCL
Connection ~ 10150 2500
Wire Wire Line
	10150 2400 10150 2500
$Comp
L power:+3.3V #PWR0116
U 1 1 5D3D7D67
P 10150 2400
F 0 "#PWR0116" H 10150 2250 50  0001 C CNN
F 1 "+3.3V" H 10165 2573 50  0000 C CNN
F 2 "" H 10150 2400 50  0001 C CNN
F 3 "" H 10150 2400 50  0001 C CNN
	1    10150 2400
	1    0    0    -1  
$EndComp
Wire Wire Line
	10100 2500 10150 2500
$Comp
L Device:C C26
U 1 1 5D3B9CDD
P 9200 2450
F 0 "C26" V 9150 2300 50  0000 L CNN
F 1 "4.7nF" V 9250 2200 50  0000 L CNN
F 2 "Capacitor_SMD:C_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 9238 2300 50  0001 C CNN
F 3 "~" H 9200 2450 50  0001 C CNN
	1    9200 2450
	-1   0    0    1   
$EndComp
$Comp
L Device:C C27
U 1 1 5D3B965C
P 10350 2350
F 0 "C27" H 10450 2550 50  0000 L CNN
F 1 "0.1uF" H 10350 2450 50  0000 L CNN
F 2 "Capacitor_SMD:C_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 10388 2200 50  0001 C CNN
F 3 "~" H 10350 2350 50  0001 C CNN
	1    10350 2350
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR0114
U 1 1 5D3AA656
P 9400 2300
F 0 "#PWR0114" H 9400 2050 50  0001 C CNN
F 1 "GND" H 9405 2127 50  0001 C CNN
F 2 "" H 9400 2300 50  0001 C CNN
F 3 "" H 9400 2300 50  0001 C CNN
	1    9400 2300
	-1   0    0    1   
$EndComp
$Comp
L brbuttonsystem:TC305 U11
U 1 1 5D398DBB
P 9500 2400
F 0 "U11" H 9550 2450 50  0000 C CNN
F 1 "TC305" H 9850 2450 50  0000 C CNN
F 2 "Package_SO:SOIC-16_3.9x9.9mm_P1.27mm" H 9500 2400 50  0001 C CNN
F 3 "" H 9500 2400 50  0001 C CNN
	1    9500 2400
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR0115
U 1 1 5D3D5CBD
P 10350 2200
F 0 "#PWR0115" H 10350 1950 50  0001 C CNN
F 1 "GND" H 10355 2027 50  0001 C CNN
F 2 "" H 10350 2200 50  0001 C CNN
F 3 "" H 10350 2200 50  0001 C CNN
	1    10350 2200
	-1   0    0    1   
$EndComp
$Sheet
S 1150 1200 650  750 
U 5D3754B9
F0 "Entertainment" 50
F1 "Entertainment.sch" 50
F2 "EXT_READ" I L 1150 1300 50 
F3 "EXT_RW" I L 1150 1400 50 
F4 "EXT_WRITE" I L 1150 1500 50 
F5 "StartLed" I L 1150 1650 50 
F6 "Sound_PWM" I L 1150 1750 50 
F7 "BuzzerEnable" I L 1150 1850 50 
$EndSheet
Text Label 1050 1300 2    50   ~ 0
EXT_READ
Text Label 1050 1400 2    50   ~ 0
EXT_RW
Text Label 1050 1500 2    50   ~ 0
EXT_WRITE
Text Label 1050 1650 2    50   ~ 0
StartLed
Text Label 1050 1750 2    50   ~ 0
Sound_PWM
Wire Wire Line
	1150 1750 1050 1750
Wire Wire Line
	1050 1650 1150 1650
Wire Wire Line
	1150 1500 1050 1500
Wire Wire Line
	1050 1400 1150 1400
Wire Wire Line
	1150 1300 1050 1300
Text GLabel 3400 5300 2    50   Input ~ 0
D+
Text GLabel 3400 5200 2    50   Input ~ 0
D-
$Sheet
S 1150 700  650  200 
U 5D4A520B
F0 "PowerInput" 50
F1 "PowerInput.sch" 50
F2 "USB_PULLUP" I L 1150 800 50 
$EndSheet
Connection ~ 3300 2900
Connection ~ 3300 3200
Text Label 3150 4100 0    50   ~ 0
BACK_LED_CS
Wire Wire Line
	1050 1850 1150 1850
Text Label 3600 6600 0    50   ~ 0
NRST
Text Label 3600 6900 0    50   ~ 0
SWCLK
Text Label 3600 6700 0    50   ~ 0
SWDIO
$Comp
L power:GND #PWR0139
U 1 1 5D4D063C
P 3600 6800
F 0 "#PWR0139" H 3600 6550 50  0001 C CNN
F 1 "GND" H 3605 6627 50  0001 C CNN
F 2 "" H 3600 6800 50  0001 C CNN
F 3 "" H 3600 6800 50  0001 C CNN
	1    3600 6800
	0    -1   -1   0   
$EndComp
$Comp
L brbuttonsystem:K3-2235D J14
U 1 1 5D77A292
P 7950 2200
F 0 "J14" H 7925 2425 50  0000 C CNN
F 1 "K3-2235D" H 7925 2334 50  0000 C CNN
F 2 "brbuttonsystem:K3-2235D" H 7950 2200 50  0001 C CNN
F 3 "" H 7950 2200 50  0001 C CNN
	1    7950 2200
	1    0    0    -1  
$EndComp
$Comp
L brbuttonsystem:K3-2235D J14
U 2 1 5D77AE79
P 7950 2950
F 0 "J14" H 7925 3175 50  0000 C CNN
F 1 "K3-2235D" H 7925 3084 50  0000 C CNN
F 2 "brbuttonsystem:K3-2235D" H 7950 2950 50  0001 C CNN
F 3 "" H 7950 2950 50  0001 C CNN
	2    7950 2950
	1    0    0    -1  
$EndComp
Wire Wire Line
	1450 5300 1850 5300
$Comp
L brbuttonsystem:LCD_1_8_SPI U12
U 1 1 5D664791
P 9800 4600
F 0 "U12" H 9800 4650 50  0000 C CNN
F 1 "LCD_1_8_SPI" H 10150 4650 50  0000 C CNN
F 2 "brbuttonsystem:LCD_1_8_SPI" H 9800 4600 50  0001 C CNN
F 3 "" H 9800 4600 50  0001 C CNN
	1    9800 4600
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR0121
U 1 1 5D66C20A
P 9700 5500
F 0 "#PWR0121" H 9700 5250 50  0001 C CNN
F 1 "GND" H 9705 5327 50  0001 C CNN
F 2 "" H 9700 5500 50  0001 C CNN
F 3 "" H 9700 5500 50  0001 C CNN
	1    9700 5500
	1    0    0    -1  
$EndComp
$Comp
L power:+3.3V #PWR0122
U 1 1 5D66EC1A
P 9700 5400
F 0 "#PWR0122" H 9700 5250 50  0001 C CNN
F 1 "+3.3V" V 9700 5650 50  0000 C CNN
F 2 "" H 9700 5400 50  0001 C CNN
F 3 "" H 9700 5400 50  0001 C CNN
	1    9700 5400
	0    -1   -1   0   
$EndComp
$Comp
L power:GND #PWR0141
U 1 1 5D68AB1C
P 10300 5500
F 0 "#PWR0141" H 10300 5250 50  0001 C CNN
F 1 "GND" H 10305 5327 50  0001 C CNN
F 2 "" H 10300 5500 50  0001 C CNN
F 3 "" H 10300 5500 50  0001 C CNN
	1    10300 5500
	1    0    0    -1  
$EndComp
Text Label 9700 5100 2    50   ~ 0
LCD_SCK
Text Label 9700 5200 2    50   ~ 0
LCD_MOSI
Text Label 9700 5000 2    50   ~ 0
LCD_DC
$Comp
L Transistor_FET:IRLML2060 Q8
U 1 1 5D68AB43
P 8900 4900
F 0 "Q8" H 8750 5000 50  0000 L CNN
F 1 "IRLML2803" H 8550 5100 50  0000 L CNN
F 2 "Package_TO_SOT_SMD:SOT-23" H 9100 4825 50  0001 L CIN
F 3 "https://www.infineon.com/dgdl/irlml2060pbf.pdf?fileId=5546d462533600a401535664b7fb25ee" H 8900 4900 50  0001 L CNN
	1    8900 4900
	1    0    0    -1  
$EndComp
$Comp
L Device:R R20
U 1 1 5D14F380
P 5250 950
F 0 "R20" V 5350 850 50  0000 C CNN
F 1 "47k" V 5350 1000 50  0000 C CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" V 5180 950 50  0001 C CNN
F 3 "~" H 5250 950 50  0001 C CNN
	1    5250 950 
	0    1    -1   0   
$EndComp
$Comp
L Device:R R12
U 1 1 5D6CBE16
P 8850 5100
F 0 "R12" V 8950 4950 50  0000 C CNN
F 1 "47k" V 8950 5150 50  0000 C CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" V 8780 5100 50  0001 C CNN
F 3 "~" H 8850 5100 50  0001 C CNN
	1    8850 5100
	0    1    1    0   
$EndComp
Wire Wire Line
	8700 4900 8700 5100
$Comp
L power:GND #PWR0142
U 1 1 5D6EB51B
P 9000 5250
F 0 "#PWR0142" H 9000 5000 50  0001 C CNN
F 1 "GND" H 9005 5077 50  0001 C CNN
F 2 "" H 9000 5250 50  0001 C CNN
F 3 "" H 9000 5250 50  0001 C CNN
	1    9000 5250
	-1   0    0    -1  
$EndComp
Connection ~ 9000 5100
Text Label 5400 1650 0    50   ~ 0
MainLcdLed
Text Label 9450 4700 0    50   ~ 0
BackLcdLed
Text Label 8850 1600 2    50   ~ 0
BTRX_MCUTX
Text Label 8850 1500 2    50   ~ 0
BTTX_MCURX
$Comp
L power:+5V #PWR0143
U 1 1 5D7D21F9
P 8450 900
F 0 "#PWR0143" H 8450 750 50  0001 C CNN
F 1 "+5V" H 8465 1073 50  0000 C CNN
F 2 "" H 8450 900 50  0001 C CNN
F 3 "" H 8450 900 50  0001 C CNN
	1    8450 900 
	1    0    0    -1  
$EndComp
$Comp
L Device:LED D14
U 1 1 5D80F7D1
P 7600 2950
F 0 "D14" H 7700 2900 50  0000 C CNN
F 1 "LED" H 7593 2786 50  0001 C CNN
F 2 "LED_THT:LED_D3.0mm_Clear" H 7600 2950 50  0001 C CNN
F 3 "~" H 7600 2950 50  0001 C CNN
	1    7600 2950
	-1   0    0    -1  
$EndComp
$Comp
L Device:R R13
U 1 1 5D811781
P 7300 2950
F 0 "R13" V 7400 2850 50  0000 C CNN
F 1 "1k" V 7400 3000 50  0000 C CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" V 7230 2950 50  0001 C CNN
F 3 "~" H 7300 2950 50  0001 C CNN
	1    7300 2950
	0    1    -1   0   
$EndComp
$Comp
L power:+3.3V #PWR0144
U 1 1 5D813CB3
P 7150 2950
F 0 "#PWR0144" H 7150 2800 50  0001 C CNN
F 1 "+3.3V" H 7165 3123 50  0000 C CNN
F 2 "" H 7150 2950 50  0001 C CNN
F 3 "" H 7150 2950 50  0001 C CNN
	1    7150 2950
	0    -1   -1   0   
$EndComp
Text Label 9700 4900 2    50   ~ 0
BACK_LCD_CS
$Comp
L brbuttonsystem:LCD_2.8_SPI U1
U 1 1 5D0C6ED3
P 6100 850
F 0 "U1" H 6150 900 50  0000 C CNN
F 1 "LCD_2.8_SPI" H 6650 900 50  0000 C CNN
F 2 "brbuttonsystem:LCD_2.8_SPI" H 6100 850 50  0001 C CNN
F 3 "" H 6100 850 50  0001 C CNN
	1    6100 850 
	1    0    0    -1  
$EndComp
Text Label 9700 4800 2    50   ~ 0
LCD_RESET
$Comp
L Connector:Conn_01x06_Male J9
U 1 1 5D728F35
P 8600 3000
F 0 "J9" H 8573 2928 50  0000 R CNN
F 1 "Conn_01x06_Male" H 8708 3290 50  0001 C CNN
F 2 "brbuttonsystem:A1501WR-S-5P" H 8600 3000 50  0001 C CNN
F 3 "~" H 8600 3000 50  0001 C CNN
	1    8600 3000
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR0119
U 1 1 5D50F518
P 8950 2300
F 0 "#PWR0119" H 8950 2050 50  0001 C CNN
F 1 "GND" H 8955 2127 50  0001 C CNN
F 2 "" H 8950 2300 50  0001 C CNN
F 3 "" H 8950 2300 50  0001 C CNN
	1    8950 2300
	-1   0    0    1   
$EndComp
$Comp
L power:GND #PWR0145
U 1 1 5D73EE93
P 8800 3300
F 0 "#PWR0145" H 8800 3050 50  0001 C CNN
F 1 "GND" H 8805 3127 50  0001 C CNN
F 2 "" H 8800 3300 50  0001 C CNN
F 3 "" H 8800 3300 50  0001 C CNN
	1    8800 3300
	1    0    0    -1  
$EndComp
Text Label 1850 3900 2    50   ~ 0
BACK_LCD_CS
Text Notes 4550 1950 0    50   ~ 0
MainLcdLed is used\nas a switch for front LCD\nand as a signal invertor\nfor back LCD switch.
Text Label 3150 5600 0    50   ~ 0
USB_PULLUP
$Comp
L Device:R_Pack04 RN2
U 1 1 5D6AA8DC
P 9150 3000
F 0 "RN2" V 8850 3100 50  0000 C CNN
F 1 "3k" V 8850 2950 50  0000 C CNN
F 2 "Resistor_SMD:R_Array_Convex_4x0603" V 9425 3000 50  0001 C CNN
F 3 "~" H 9150 3000 50  0001 C CNN
	1    9150 3000
	0    1    -1   0   
$EndComp
Wire Wire Line
	8950 2900 8800 2900
Wire Wire Line
	8800 3000 8950 3000
Wire Wire Line
	8950 3100 8800 3100
Wire Wire Line
	8800 3200 8950 3200
Text Label 1050 800  2    50   ~ 0
USB_PULLUP
Wire Wire Line
	1050 800  1150 800 
$Comp
L Device:R R15
U 1 1 5D6C1AC9
P 8250 2400
F 0 "R15" V 8350 2300 50  0000 C CNN
F 1 "47k" V 8350 2450 50  0000 C CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" V 8180 2400 50  0001 C CNN
F 3 "~" H 8250 2400 50  0001 C CNN
	1    8250 2400
	0    1    1    0   
$EndComp
$Comp
L power:+3.3V #PWR0146
U 1 1 5D6C3AA5
P 8400 2400
F 0 "#PWR0146" H 8400 2250 50  0001 C CNN
F 1 "+3.3V" V 8300 2450 50  0000 C CNN
F 2 "" H 8400 2400 50  0001 C CNN
F 3 "" H 8400 2400 50  0001 C CNN
	1    8400 2400
	0    1    1    0   
$EndComp
$Comp
L power:GND #PWR0147
U 1 1 5EBC5070
P 7000 1450
F 0 "#PWR0147" H 7000 1200 50  0001 C CNN
F 1 "GND" H 7005 1277 50  0001 C CNN
F 2 "" H 7000 1450 50  0001 C CNN
F 3 "" H 7000 1450 50  0001 C CNN
	1    7000 1450
	0    -1   -1   0   
$EndComp
$Comp
L power:GND #PWR0148
U 1 1 5EBC55DD
P 7000 1650
F 0 "#PWR0148" H 7000 1400 50  0001 C CNN
F 1 "GND" H 7005 1477 50  0001 C CNN
F 2 "" H 7000 1650 50  0001 C CNN
F 3 "" H 7000 1650 50  0001 C CNN
	1    7000 1650
	0    -1   -1   0   
$EndComp
$Comp
L power:+5V #PWR0149
U 1 1 5EBC6CA1
P 7000 1550
F 0 "#PWR0149" H 7000 1400 50  0001 C CNN
F 1 "+5V" H 7015 1723 50  0000 C CNN
F 2 "" H 7000 1550 50  0001 C CNN
F 3 "" H 7000 1550 50  0001 C CNN
	1    7000 1550
	0    1    1    0   
$EndComp
$Sheet
S 2850 700  650  900 
U 5ECDBE8F
F0 "Buttons" 50
F1 "Buttons.sch" 50
F2 "LCD_MOSI" I L 2850 800 50 
F3 "LCD_SCK" I L 2850 900 50 
F4 "BACK_LED_CS" I L 2850 1000 50 
F5 "But0" I L 2850 1200 50 
F6 "But1" I L 2850 1300 50 
F7 "But2" I L 2850 1400 50 
F8 "But3" I L 2850 1500 50 
$EndSheet
Text Label 2750 800  2    50   ~ 0
LCD_MOSI
Text Label 2750 900  2    50   ~ 0
LCD_SCK
Text Label 2750 1000 2    50   ~ 0
BACK_LED_CS
Wire Wire Line
	2750 1000 2850 1000
Wire Wire Line
	2850 900  2750 900 
Wire Wire Line
	2750 800  2850 800 
Text Label 2750 1200 2    50   ~ 0
But0
Text Label 2750 1300 2    50   ~ 0
But1
Text Label 2750 1400 2    50   ~ 0
But2
Text Label 2750 1500 2    50   ~ 0
But3
Wire Wire Line
	2750 1200 2850 1200
Wire Wire Line
	2850 1300 2750 1300
Wire Wire Line
	2750 1400 2850 1400
Wire Wire Line
	2850 1500 2750 1500
$Comp
L power:GND #PWR0168
U 1 1 5F134F3D
P 1300 3200
F 0 "#PWR0168" H 1300 2950 50  0001 C CNN
F 1 "GND" H 1305 3027 50  0001 C CNN
F 2 "" H 1300 3200 50  0001 C CNN
F 3 "" H 1300 3200 50  0001 C CNN
	1    1300 3200
	0    1    1    0   
$EndComp
Wire Wire Line
	1600 3200 1850 3200
Text Label 1850 3000 2    50   ~ 0
NRST
Text Label 8700 4900 2    50   ~ 0
MainLcdLed
$Comp
L Device:R R50
U 1 1 5F13C9FC
P 1450 3200
F 0 "R50" V 1350 3100 50  0000 C CNN
F 1 "100k" V 1350 3300 50  0000 C CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" V 1380 3200 50  0001 C CNN
F 3 "~" H 1450 3200 50  0001 C CNN
	1    1450 3200
	0    1    1    0   
$EndComp
$Comp
L power:+3.3V #PWR0169
U 1 1 5F1D7298
P 3600 7100
F 0 "#PWR0169" H 3600 6950 50  0001 C CNN
F 1 "+3.3V" V 3600 7350 50  0000 C CNN
F 2 "" H 3600 7100 50  0001 C CNN
F 3 "" H 3600 7100 50  0001 C CNN
	1    3600 7100
	0    1    1    0   
$EndComp
$Comp
L Device:C C5
U 1 1 5F20E3F6
P 10550 5150
F 0 "C5" H 10665 5196 50  0000 L CNN
F 1 "0.1uF" H 10665 5105 50  0000 L CNN
F 2 "Capacitor_SMD:C_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 10588 5000 50  0001 C CNN
F 3 "~" H 10550 5150 50  0001 C CNN
	1    10550 5150
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR0170
U 1 1 5F20F286
P 10550 5300
F 0 "#PWR0170" H 10550 5050 50  0001 C CNN
F 1 "GND" H 10555 5127 50  0001 C CNN
F 2 "" H 10550 5300 50  0001 C CNN
F 3 "" H 10550 5300 50  0001 C CNN
	1    10550 5300
	1    0    0    -1  
$EndComp
$Comp
L power:+3.3V #PWR0171
U 1 1 5F20F54B
P 10550 5000
F 0 "#PWR0171" H 10550 4850 50  0001 C CNN
F 1 "+3.3V" H 10550 5150 50  0000 C CNN
F 2 "" H 10550 5000 50  0001 C CNN
F 3 "" H 10550 5000 50  0001 C CNN
	1    10550 5000
	1    0    0    -1  
$EndComp
Wire Wire Line
	9000 4700 9700 4700
Wire Wire Line
	9000 5250 9000 5100
$Comp
L Device:R_Pack04 RN1
U 1 1 603F1D07
P 1050 5400
F 0 "RN1" V 750 5350 50  0000 C CNN
F 1 "47k" V 750 5500 50  0000 C CNN
F 2 "Resistor_SMD:R_Array_Convex_4x0603" V 1325 5400 50  0001 C CNN
F 3 "~" H 1050 5400 50  0001 C CNN
	1    1050 5400
	0    1    -1   0   
$EndComp
$Comp
L power:+3.3V #PWR0179
U 1 1 6040BC1B
P 1300 4950
F 0 "#PWR0179" H 1300 4800 50  0001 C CNN
F 1 "+3.3V" V 1300 5200 50  0000 C CNN
F 2 "" H 1300 4950 50  0001 C CNN
F 3 "" H 1300 4950 50  0001 C CNN
	1    1300 4950
	1    0    0    -1  
$EndComp
Wire Wire Line
	1400 5400 1400 5150
Wire Wire Line
	1400 5150 750  5150
Wire Wire Line
	750  5150 750  5300
Wire Wire Line
	750  5300 850  5300
Wire Wire Line
	1400 5400 1850 5400
Wire Wire Line
	1450 5300 1450 5050
Wire Wire Line
	1450 5050 650  5050
$Comp
L power:+3.3V #PWR030
U 1 1 5CFE0A9B
P 750 5800
F 0 "#PWR030" H 750 5650 50  0001 C CNN
F 1 "+3.3V" V 750 6050 50  0000 C CNN
F 2 "" H 750 5800 50  0001 C CNN
F 3 "" H 750 5800 50  0001 C CNN
	1    750  5800
	-1   0    0    1   
$EndComp
Wire Wire Line
	850  5500 650  5500
Wire Wire Line
	650  5050 650  5500
Wire Wire Line
	850  5400 750  5400
Wire Wire Line
	750  5400 750  5600
Wire Wire Line
	850  5600 750  5600
Connection ~ 750  5600
Wire Wire Line
	750  5600 750  5800
Wire Wire Line
	1250 5400 1350 5400
Wire Wire Line
	1250 5500 1300 5500
Wire Wire Line
	1300 5500 1300 5300
Wire Wire Line
	1250 5300 1300 5300
Connection ~ 1300 5300
Wire Wire Line
	1300 5300 1300 4950
Wire Wire Line
	1350 5600 1350 5400
Wire Wire Line
	1850 5600 1350 5600
Wire Wire Line
	1850 5500 1550 5500
Wire Wire Line
	1550 5500 1550 5650
Wire Wire Line
	1550 5650 1300 5650
Wire Wire Line
	1300 5650 1300 5600
Wire Wire Line
	1300 5600 1250 5600
$Comp
L Connector:Conn_01x06_Male J11
U 1 1 6049F9DA
P 5250 4850
F 0 "J11" H 5358 5139 50  0000 C CNN
F 1 "Conn_01x06_Male" H 5358 5140 50  0001 C CNN
F 2 "brbuttonsystem:JST_ZH_B6B-ZR_1x06_P1.50mm_Vertical" H 5250 4850 50  0001 C CNN
F 3 "~" H 5250 4850 50  0001 C CNN
	1    5250 4850
	1    0    0    -1  
$EndComp
Text Label 5450 4650 0    50   ~ 0
But0
Text Label 5450 4750 0    50   ~ 0
But1
Text Label 5450 4850 0    50   ~ 0
But2
Text Label 5450 4950 0    50   ~ 0
But3
$Comp
L power:+3.3V #PWR0180
U 1 1 604A1AF3
P 5450 5150
F 0 "#PWR0180" H 5450 5000 50  0001 C CNN
F 1 "+3.3V" H 5465 5323 50  0000 C CNN
F 2 "" H 5450 5150 50  0001 C CNN
F 3 "" H 5450 5150 50  0001 C CNN
	1    5450 5150
	0    1    1    0   
$EndComp
$Comp
L power:GND #PWR0181
U 1 1 604A1FB6
P 5450 5050
F 0 "#PWR0181" H 5450 4800 50  0001 C CNN
F 1 "GND" H 5455 4877 50  0001 C CNN
F 2 "" H 5450 5050 50  0001 C CNN
F 3 "" H 5450 5050 50  0001 C CNN
	1    5450 5050
	0    -1   -1   0   
$EndComp
$Comp
L Connector:Conn_01x06_Male J10
U 1 1 607FBEF0
P 3400 6800
F 0 "J10" H 3508 7089 50  0000 C CNN
F 1 "Conn_01x06_Male" H 3508 7090 50  0001 C CNN
F 2 "Connector_PinSocket_2.54mm:PinSocket_1x06_P2.54mm_Vertical" H 3400 6800 50  0001 C CNN
F 3 "~" H 3400 6800 50  0001 C CNN
	1    3400 6800
	1    0    0    -1  
$EndComp
NoConn ~ 3600 7000
$EndSCHEMATC
