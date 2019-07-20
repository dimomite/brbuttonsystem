EESchema Schematic File Version 4
LIBS:example-cache
EELAYER 29 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 1 1
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
L brbuttonsystem:BluePill U1
U 1 1 5D33EA46
P 4250 1500
F 0 "U1" H 4300 1550 50  0000 C CNN
F 1 "BluePill" H 5050 1550 50  0000 C CNN
F 2 "brbuttonsystem:DIP40_module" H 4250 1500 50  0001 C CNN
F 3 "" H 4250 1500 50  0001 C CNN
	1    4250 1500
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR018
U 1 1 5D342C75
P 5300 1600
F 0 "#PWR018" H 5300 1350 50  0001 C CNN
F 1 "GND" H 5305 1427 50  0001 C CNN
F 2 "" H 5300 1600 50  0001 C CNN
F 3 "" H 5300 1600 50  0001 C CNN
	1    5300 1600
	0    -1   -1   0   
$EndComp
$Comp
L power:GND #PWR016
U 1 1 5D34341B
P 4150 3400
F 0 "#PWR016" H 4150 3150 50  0001 C CNN
F 1 "GND" H 4155 3227 50  0001 C CNN
F 2 "" H 4150 3400 50  0001 C CNN
F 3 "" H 4150 3400 50  0001 C CNN
	1    4150 3400
	0    1    1    0   
$EndComp
Wire Wire Line
	5300 1700 5300 1600
Connection ~ 5300 1600
$Comp
L power:+3.3V #PWR019
U 1 1 5D3441C7
P 5300 1800
F 0 "#PWR019" H 5300 1650 50  0001 C CNN
F 1 "+3.3V" V 5315 1928 50  0000 L CNN
F 2 "" H 5300 1800 50  0001 C CNN
F 3 "" H 5300 1800 50  0001 C CNN
	1    5300 1800
	0    1    1    0   
$EndComp
$Comp
L power:+3.3V #PWR017
U 1 1 5D344998
P 4150 3500
F 0 "#PWR017" H 4150 3350 50  0001 C CNN
F 1 "+3.3V" V 4165 3628 50  0000 L CNN
F 2 "" H 4150 3500 50  0001 C CNN
F 3 "" H 4150 3500 50  0001 C CNN
	1    4150 3500
	0    -1   -1   0   
$EndComp
$Comp
L power:+5V #PWR015
U 1 1 5D34592A
P 4150 3300
F 0 "#PWR015" H 4150 3150 50  0001 C CNN
F 1 "+5V" V 4165 3428 50  0000 L CNN
F 2 "" H 4150 3300 50  0001 C CNN
F 3 "" H 4150 3300 50  0001 C CNN
	1    4150 3300
	0    -1   -1   0   
$EndComp
$Comp
L Connector:RJ12 J1
U 1 1 5D346B6E
P 1200 1550
F 0 "J1" H 950 1200 50  0000 C CNN
F 1 "RJ12" H 1450 1200 50  0000 C CNN
F 2 "" V 1200 1575 50  0001 C CNN
F 3 "~" V 1200 1575 50  0001 C CNN
	1    1200 1550
	1    0    0    1   
$EndComp
$Comp
L Comparator:LMV339 U2
U 1 1 5D347850
P 2700 1600
F 0 "U2" H 2750 1350 50  0000 C CNN
F 1 "LMV339" H 2850 1450 50  0000 C CNN
F 2 "" H 2650 1700 50  0001 C CNN
F 3 "http://www.ti.com/lit/ds/symlink/lmv331.pdf" H 2750 1800 50  0001 C CNN
	1    2700 1600
	1    0    0    1   
$EndComp
$Comp
L Comparator:LMV339 U2
U 2 1 5D3489D0
P 2700 2550
F 0 "U2" H 2750 2300 50  0000 C CNN
F 1 "LMV339" H 2850 2400 50  0000 C CNN
F 2 "" H 2650 2650 50  0001 C CNN
F 3 "http://www.ti.com/lit/ds/symlink/lmv331.pdf" H 2750 2750 50  0001 C CNN
	2    2700 2550
	1    0    0    1   
$EndComp
$Comp
L Comparator:LMV339 U2
U 3 1 5D348F80
P 2700 3500
F 0 "U2" H 2750 3250 50  0000 C CNN
F 1 "LMV339" H 2850 3350 50  0000 C CNN
F 2 "" H 2650 3600 50  0001 C CNN
F 3 "http://www.ti.com/lit/ds/symlink/lmv331.pdf" H 2750 3700 50  0001 C CNN
	3    2700 3500
	1    0    0    1   
$EndComp
$Comp
L Comparator:LMV339 U2
U 4 1 5D349387
P 2700 4450
F 0 "U2" H 2750 4200 50  0000 C CNN
F 1 "LMV339" H 2850 4300 50  0000 C CNN
F 2 "" H 2650 4550 50  0001 C CNN
F 3 "http://www.ti.com/lit/ds/symlink/lmv331.pdf" H 2750 4650 50  0001 C CNN
	4    2700 4450
	1    0    0    1   
$EndComp
$Comp
L Comparator:LMV339 U2
U 5 1 5D34AB84
P 2100 900
F 0 "U2" V 1900 750 50  0000 C CNN
F 1 "LMV339" V 1900 1050 50  0000 C CNN
F 2 "" H 2050 1000 50  0001 C CNN
F 3 "http://www.ti.com/lit/ds/symlink/lmv331.pdf" H 2150 1100 50  0001 C CNN
	5    2100 900 
	0    1    1    0   
$EndComp
$Comp
L power:+5V #PWR02
U 1 1 5D34BCE6
P 2400 800
F 0 "#PWR02" H 2400 650 50  0001 C CNN
F 1 "+5V" V 2415 928 50  0000 L CNN
F 2 "" H 2400 800 50  0001 C CNN
F 3 "" H 2400 800 50  0001 C CNN
	1    2400 800 
	0    1    1    0   
$EndComp
$Comp
L power:GND #PWR01
U 1 1 5D34C4EC
P 1800 800
F 0 "#PWR01" H 1800 550 50  0001 C CNN
F 1 "GND" H 1805 627 50  0001 C CNN
F 2 "" H 1800 800 50  0001 C CNN
F 3 "" H 1800 800 50  0001 C CNN
	1    1800 800 
	0    1    1    0   
$EndComp
$Comp
L Device:R R9
U 1 1 5D34F7AB
P 3200 1350
F 0 "R9" H 3270 1396 50  0000 L CNN
F 1 "4k7" H 3270 1305 50  0000 L CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 3130 1350 50  0001 C CNN
F 3 "~" H 3200 1350 50  0001 C CNN
	1    3200 1350
	1    0    0    -1  
$EndComp
$Comp
L Device:R R1
U 1 1 5D350056
P 1950 1450
F 0 "R1" V 1850 1350 50  0000 C CNN
F 1 "100k" V 1850 1550 50  0000 C CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 1880 1450 50  0001 C CNN
F 3 "~" H 1950 1450 50  0001 C CNN
	1    1950 1450
	0    1    1    0   
$EndComp
$Comp
L Connector:RJ12 J2
U 1 1 5D3510AB
P 1200 2500
F 0 "J2" H 950 2150 50  0000 C CNN
F 1 "RJ12" H 1450 2150 50  0000 C CNN
F 2 "" V 1200 2525 50  0001 C CNN
F 3 "~" V 1200 2525 50  0001 C CNN
	1    1200 2500
	1    0    0    1   
$EndComp
$Comp
L Connector:RJ12 J3
U 1 1 5D351491
P 1200 3450
F 0 "J3" H 950 3100 50  0000 C CNN
F 1 "RJ12" H 1450 3100 50  0000 C CNN
F 2 "" V 1200 3475 50  0001 C CNN
F 3 "~" V 1200 3475 50  0001 C CNN
	1    1200 3450
	1    0    0    1   
$EndComp
$Comp
L Connector:RJ12 J4
U 1 1 5D3518AE
P 1200 4400
F 0 "J4" H 950 4050 50  0000 C CNN
F 1 "RJ12" H 1450 4050 50  0000 C CNN
F 2 "" V 1200 4425 50  0001 C CNN
F 3 "~" V 1200 4425 50  0001 C CNN
	1    1200 4400
	1    0    0    1   
$EndComp
$Comp
L Device:C C1
U 1 1 5D3530F7
P 2100 950
F 0 "C1" V 1950 1050 50  0000 C CNN
F 1 "0.1uF" V 1950 850 50  0000 C CNN
F 2 "Capacitor_THT:C_Disc_D5.0mm_W2.5mm_P5.00mm" H 2138 800 50  0001 C CNN
F 3 "~" H 2100 950 50  0001 C CNN
	1    2100 950 
	0    -1   -1   0   
$EndComp
Wire Wire Line
	2400 800  2400 950 
Wire Wire Line
	2400 950  2250 950 
Connection ~ 2400 800 
Wire Wire Line
	1800 800  1800 950 
Wire Wire Line
	1800 950  1950 950 
Connection ~ 1800 800 
$Comp
L Device:R R2
U 1 1 5D356753
P 1950 1750
F 0 "R2" V 2050 1650 50  0000 C CNN
F 1 "100k" V 2050 1850 50  0000 C CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 1880 1750 50  0001 C CNN
F 3 "~" H 1950 1750 50  0001 C CNN
	1    1950 1750
	0    1    1    0   
$EndComp
$Comp
L Device:R R3
U 1 1 5D3579E3
P 1950 2400
F 0 "R3" V 1850 2300 50  0000 C CNN
F 1 "100k" V 1850 2500 50  0000 C CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 1880 2400 50  0001 C CNN
F 3 "~" H 1950 2400 50  0001 C CNN
	1    1950 2400
	0    1    1    0   
$EndComp
$Comp
L Device:R R4
U 1 1 5D357D74
P 1950 2700
F 0 "R4" V 2050 2600 50  0000 C CNN
F 1 "100k" V 2050 2800 50  0000 C CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 1880 2700 50  0001 C CNN
F 3 "~" H 1950 2700 50  0001 C CNN
	1    1950 2700
	0    1    1    0   
$EndComp
$Comp
L Device:R R5
U 1 1 5D359B14
P 1950 3350
F 0 "R5" V 1850 3250 50  0000 C CNN
F 1 "100k" V 1850 3450 50  0000 C CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 1880 3350 50  0001 C CNN
F 3 "~" H 1950 3350 50  0001 C CNN
	1    1950 3350
	0    1    1    0   
$EndComp
$Comp
L Device:R R6
U 1 1 5D359FB6
P 1950 3650
F 0 "R6" V 2050 3550 50  0000 C CNN
F 1 "100k" V 2050 3750 50  0000 C CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 1880 3650 50  0001 C CNN
F 3 "~" H 1950 3650 50  0001 C CNN
	1    1950 3650
	0    1    1    0   
$EndComp
$Comp
L Device:R R7
U 1 1 5D35AE9B
P 1950 4300
F 0 "R7" V 1850 4200 50  0000 C CNN
F 1 "100k" V 1850 4400 50  0000 C CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 1880 4300 50  0001 C CNN
F 3 "~" H 1950 4300 50  0001 C CNN
	1    1950 4300
	0    1    1    0   
$EndComp
$Comp
L Device:R R8
U 1 1 5D35B211
P 1950 4600
F 0 "R8" V 2050 4500 50  0000 C CNN
F 1 "100k" V 2050 4700 50  0000 C CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 1880 4600 50  0001 C CNN
F 3 "~" H 1950 4600 50  0001 C CNN
	1    1950 4600
	0    1    1    0   
$EndComp
$Comp
L Device:R R10
U 1 1 5D35C104
P 3200 2300
F 0 "R10" H 3270 2346 50  0000 L CNN
F 1 "4k7" H 3270 2255 50  0000 L CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 3130 2300 50  0001 C CNN
F 3 "~" H 3200 2300 50  0001 C CNN
	1    3200 2300
	1    0    0    -1  
$EndComp
$Comp
L Device:R R11
U 1 1 5D35C29D
P 3200 3250
F 0 "R11" H 3270 3296 50  0000 L CNN
F 1 "4k7" H 3270 3205 50  0000 L CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 3130 3250 50  0001 C CNN
F 3 "~" H 3200 3250 50  0001 C CNN
	1    3200 3250
	1    0    0    -1  
$EndComp
$Comp
L Device:R R12
U 1 1 5D35C45B
P 3200 4200
F 0 "R12" H 3270 4246 50  0000 L CNN
F 1 "4k7" H 3270 4155 50  0000 L CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 3130 4200 50  0001 C CNN
F 3 "~" H 3200 4200 50  0001 C CNN
	1    3200 4200
	1    0    0    -1  
$EndComp
Wire Wire Line
	1600 4300 1700 4300
Wire Wire Line
	1600 4400 2100 4400
Wire Wire Line
	2100 4400 2100 4350
Wire Wire Line
	1600 4600 1700 4600
Wire Wire Line
	1600 4500 2100 4500
Wire Wire Line
	2100 4500 2100 4550
Wire Wire Line
	2400 4550 2100 4550
Connection ~ 2100 4550
Wire Wire Line
	2100 4550 2100 4600
Wire Wire Line
	2400 4350 2100 4350
Connection ~ 2100 4350
Wire Wire Line
	2100 4350 2100 4300
Wire Wire Line
	1600 3650 1700 3650
Wire Wire Line
	1600 3550 2100 3550
Wire Wire Line
	2100 3550 2100 3600
Wire Wire Line
	1600 3350 1700 3350
Wire Wire Line
	1600 3450 2100 3450
Wire Wire Line
	2100 3450 2100 3400
Wire Wire Line
	2400 3400 2100 3400
Connection ~ 2100 3400
Wire Wire Line
	2100 3400 2100 3350
Wire Wire Line
	2400 3600 2100 3600
Connection ~ 2100 3600
Wire Wire Line
	2100 3600 2100 3650
Wire Wire Line
	1800 2400 1700 2400
Wire Wire Line
	1600 2700 1700 2700
Wire Wire Line
	1600 2600 2100 2600
Wire Wire Line
	2100 2600 2100 2650
Wire Wire Line
	1600 2500 2100 2500
Wire Wire Line
	2100 2500 2100 2450
Wire Wire Line
	2400 2450 2100 2450
Connection ~ 2100 2450
Wire Wire Line
	2100 2450 2100 2400
Wire Wire Line
	2400 2650 2100 2650
Connection ~ 2100 2650
Wire Wire Line
	2100 2650 2100 2700
Wire Wire Line
	1600 1750 1700 1750
Wire Wire Line
	1600 1650 2100 1650
Wire Wire Line
	2100 1650 2100 1700
Wire Wire Line
	1600 1550 2100 1550
Wire Wire Line
	2100 1550 2100 1500
Wire Wire Line
	1800 1450 1700 1450
Wire Wire Line
	2400 1500 2100 1500
Connection ~ 2100 1500
Wire Wire Line
	2100 1500 2100 1450
Wire Wire Line
	2400 1700 2100 1700
Connection ~ 2100 1700
Wire Wire Line
	2100 1700 2100 1750
Wire Wire Line
	3000 1600 3200 1600
Wire Wire Line
	3200 1600 3200 1500
Wire Wire Line
	3000 2550 3200 2550
Wire Wire Line
	3200 2550 3200 2450
Wire Wire Line
	3200 3500 3200 3400
Wire Wire Line
	3000 3500 3200 3500
Wire Wire Line
	3000 4450 3200 4450
Wire Wire Line
	3200 4450 3200 4350
$Comp
L power:+5V #PWR03
U 1 1 5D36CCAD
P 1700 1450
F 0 "#PWR03" H 1700 1300 50  0001 C CNN
F 1 "+5V" H 1715 1623 50  0000 C CNN
F 2 "" H 1700 1450 50  0001 C CNN
F 3 "" H 1700 1450 50  0001 C CNN
	1    1700 1450
	1    0    0    -1  
$EndComp
Connection ~ 1700 1450
Wire Wire Line
	1700 1450 1600 1450
$Comp
L power:+5V #PWR05
U 1 1 5D36D4A8
P 1700 2400
F 0 "#PWR05" H 1700 2250 50  0001 C CNN
F 1 "+5V" H 1715 2573 50  0000 C CNN
F 2 "" H 1700 2400 50  0001 C CNN
F 3 "" H 1700 2400 50  0001 C CNN
	1    1700 2400
	1    0    0    -1  
$EndComp
Connection ~ 1700 2400
Wire Wire Line
	1700 2400 1600 2400
$Comp
L power:+5V #PWR07
U 1 1 5D36D7C2
P 1700 3350
F 0 "#PWR07" H 1700 3200 50  0001 C CNN
F 1 "+5V" H 1715 3523 50  0000 C CNN
F 2 "" H 1700 3350 50  0001 C CNN
F 3 "" H 1700 3350 50  0001 C CNN
	1    1700 3350
	1    0    0    -1  
$EndComp
Connection ~ 1700 3350
Wire Wire Line
	1700 3350 1800 3350
$Comp
L power:+5V #PWR09
U 1 1 5D36DABC
P 1700 4300
F 0 "#PWR09" H 1700 4150 50  0001 C CNN
F 1 "+5V" H 1715 4473 50  0000 C CNN
F 2 "" H 1700 4300 50  0001 C CNN
F 3 "" H 1700 4300 50  0001 C CNN
	1    1700 4300
	1    0    0    -1  
$EndComp
Connection ~ 1700 4300
Wire Wire Line
	1700 4300 1800 4300
$Comp
L power:GND #PWR04
U 1 1 5D36DF61
P 1700 1750
F 0 "#PWR04" H 1700 1500 50  0001 C CNN
F 1 "GND" H 1705 1577 50  0001 C CNN
F 2 "" H 1700 1750 50  0001 C CNN
F 3 "" H 1700 1750 50  0001 C CNN
	1    1700 1750
	1    0    0    -1  
$EndComp
Connection ~ 1700 1750
Wire Wire Line
	1700 1750 1800 1750
$Comp
L power:GND #PWR06
U 1 1 5D36E7D9
P 1700 2700
F 0 "#PWR06" H 1700 2450 50  0001 C CNN
F 1 "GND" H 1705 2527 50  0001 C CNN
F 2 "" H 1700 2700 50  0001 C CNN
F 3 "" H 1700 2700 50  0001 C CNN
	1    1700 2700
	1    0    0    -1  
$EndComp
Connection ~ 1700 2700
Wire Wire Line
	1700 2700 1800 2700
$Comp
L power:GND #PWR08
U 1 1 5D36EB50
P 1700 3650
F 0 "#PWR08" H 1700 3400 50  0001 C CNN
F 1 "GND" H 1705 3477 50  0001 C CNN
F 2 "" H 1700 3650 50  0001 C CNN
F 3 "" H 1700 3650 50  0001 C CNN
	1    1700 3650
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR010
U 1 1 5D36EE82
P 1700 4600
F 0 "#PWR010" H 1700 4350 50  0001 C CNN
F 1 "GND" H 1705 4427 50  0001 C CNN
F 2 "" H 1700 4600 50  0001 C CNN
F 3 "" H 1700 4600 50  0001 C CNN
	1    1700 4600
	1    0    0    -1  
$EndComp
Connection ~ 1700 4600
Wire Wire Line
	1700 4600 1800 4600
Connection ~ 1700 3650
Wire Wire Line
	1700 3650 1800 3650
$Comp
L power:+3.3V #PWR011
U 1 1 5D37082E
P 3200 1200
F 0 "#PWR011" H 3200 1050 50  0001 C CNN
F 1 "+3.3V" H 3215 1373 50  0000 C CNN
F 2 "" H 3200 1200 50  0001 C CNN
F 3 "" H 3200 1200 50  0001 C CNN
	1    3200 1200
	1    0    0    -1  
$EndComp
$Comp
L power:+3.3V #PWR012
U 1 1 5D370FA1
P 3200 2150
F 0 "#PWR012" H 3200 2000 50  0001 C CNN
F 1 "+3.3V" H 3215 2323 50  0000 C CNN
F 2 "" H 3200 2150 50  0001 C CNN
F 3 "" H 3200 2150 50  0001 C CNN
	1    3200 2150
	1    0    0    -1  
$EndComp
$Comp
L power:+3.3V #PWR013
U 1 1 5D3713D2
P 3200 3100
F 0 "#PWR013" H 3200 2950 50  0001 C CNN
F 1 "+3.3V" H 3215 3273 50  0000 C CNN
F 2 "" H 3200 3100 50  0001 C CNN
F 3 "" H 3200 3100 50  0001 C CNN
	1    3200 3100
	1    0    0    -1  
$EndComp
$Comp
L power:+3.3V #PWR014
U 1 1 5D3717AA
P 3200 4050
F 0 "#PWR014" H 3200 3900 50  0001 C CNN
F 1 "+3.3V" H 3215 4223 50  0000 C CNN
F 2 "" H 3200 4050 50  0001 C CNN
F 3 "" H 3200 4050 50  0001 C CNN
	1    3200 4050
	1    0    0    -1  
$EndComp
Text Label 4150 1600 2    50   ~ 0
But0
Text Label 4150 1700 2    50   ~ 0
But1
Text Label 4150 1800 2    50   ~ 0
But2
Text Label 4150 1900 2    50   ~ 0
But3
Wire Wire Line
	3200 2550 3500 2550
Wire Wire Line
	3500 2550 3500 1700
Connection ~ 3200 2550
Wire Wire Line
	3200 3500 3600 3500
Wire Wire Line
	3600 3500 3600 1800
Connection ~ 3200 3500
Wire Wire Line
	3200 4450 3700 4450
Wire Wire Line
	3700 4450 3700 1900
Connection ~ 3200 4450
Wire Wire Line
	4150 1600 3200 1600
Connection ~ 3200 1600
Wire Wire Line
	3500 1700 4150 1700
Wire Wire Line
	4150 1800 3600 1800
Wire Wire Line
	3700 1900 4150 1900
$Comp
L Connector:RJ12 J5
U 1 1 5D32F17B
P 1450 6300
F 0 "J5" H 1200 5950 50  0000 C CNN
F 1 "RJ12" H 1700 5950 50  0000 C CNN
F 2 "" V 1450 6325 50  0001 C CNN
F 3 "~" V 1450 6325 50  0001 C CNN
	1    1450 6300
	1    0    0    1   
$EndComp
$Comp
L Switch:SW_Push SW1
U 1 1 5D330301
P 2650 5950
F 0 "SW1" H 2800 6100 50  0000 C CNN
F 1 "SW_Push" H 2650 6144 50  0001 C CNN
F 2 "" H 2650 6150 50  0001 C CNN
F 3 "~" H 2650 6150 50  0001 C CNN
	1    2650 5950
	0    1    1    0   
$EndComp
$Comp
L Device:R R15
U 1 1 5D330D5E
P 2650 6900
F 0 "R15" H 2720 6946 50  0000 L CNN
F 1 "4k7" H 2720 6855 50  0000 L CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 2580 6900 50  0001 C CNN
F 3 "~" H 2650 6900 50  0001 C CNN
	1    2650 6900
	1    0    0    -1  
$EndComp
$Comp
L Device:R R13
U 1 1 5D331157
P 2250 5850
F 0 "R13" H 2320 5896 50  0000 L CNN
F 1 "10k" H 2320 5805 50  0000 L CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 2180 5850 50  0001 C CNN
F 3 "~" H 2250 5850 50  0001 C CNN
	1    2250 5850
	1    0    0    -1  
$EndComp
$Comp
L Device:R R14
U 1 1 5D3312C4
P 2250 6900
F 0 "R14" H 2320 6946 50  0000 L CNN
F 1 "10k" H 2320 6855 50  0000 L CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 2180 6900 50  0001 C CNN
F 3 "~" H 2250 6900 50  0001 C CNN
	1    2250 6900
	1    0    0    -1  
$EndComp
Wire Wire Line
	1850 6300 2250 6300
Wire Wire Line
	2250 6300 2250 6000
Wire Wire Line
	2250 6750 2250 6300
Connection ~ 2250 6300
Text Label 2000 6300 0    50   ~ 0
MiddlePoint
Wire Wire Line
	1850 6200 2000 6200
Wire Wire Line
	2000 6200 2000 5700
Wire Wire Line
	2000 5700 2250 5700
Wire Wire Line
	2250 7050 2650 7050
Wire Wire Line
	2650 6150 2650 6400
Wire Wire Line
	1850 6400 2650 6400
Connection ~ 2650 6400
Wire Wire Line
	2650 6400 2650 6750
Wire Wire Line
	2250 5700 2650 5700
Wire Wire Line
	2650 5700 2650 5750
Connection ~ 2250 5700
Wire Wire Line
	1850 6500 2050 6500
Wire Wire Line
	2050 6500 2050 7050
Wire Wire Line
	2050 7050 2250 7050
Connection ~ 2250 7050
Wire Notes Line
	900  5550 3050 5550
Text Notes 1250 7000 0    50   ~ 0
Single button\nwithout lamps
Wire Notes Line
	900  5550 900  7150
Wire Notes Line
	900  7150 3050 7150
Wire Notes Line
	3050 7150 3050 5550
$Comp
L brbuttonsystem:BluePill U3
U 1 1 5D359A88
P 9350 1850
F 0 "U3" H 9400 1900 50  0000 C CNN
F 1 "BluePill" H 10150 1900 50  0000 C CNN
F 2 "brbuttonsystem:DIP40_module" H 9350 1850 50  0001 C CNN
F 3 "" H 9350 1850 50  0001 C CNN
	1    9350 1850
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR0101
U 1 1 5D359A8E
P 10400 1950
F 0 "#PWR0101" H 10400 1700 50  0001 C CNN
F 1 "GND" H 10405 1777 50  0001 C CNN
F 2 "" H 10400 1950 50  0001 C CNN
F 3 "" H 10400 1950 50  0001 C CNN
	1    10400 1950
	0    -1   -1   0   
$EndComp
$Comp
L power:GND #PWR0102
U 1 1 5D359A94
P 9250 3750
F 0 "#PWR0102" H 9250 3500 50  0001 C CNN
F 1 "GND" H 9255 3577 50  0001 C CNN
F 2 "" H 9250 3750 50  0001 C CNN
F 3 "" H 9250 3750 50  0001 C CNN
	1    9250 3750
	0    1    1    0   
$EndComp
Wire Wire Line
	10400 2050 10400 1950
Connection ~ 10400 1950
$Comp
L power:+3.3V #PWR0103
U 1 1 5D359A9C
P 10400 2150
F 0 "#PWR0103" H 10400 2000 50  0001 C CNN
F 1 "+3.3V" V 10415 2278 50  0000 L CNN
F 2 "" H 10400 2150 50  0001 C CNN
F 3 "" H 10400 2150 50  0001 C CNN
	1    10400 2150
	0    1    1    0   
$EndComp
$Comp
L power:+3.3V #PWR0104
U 1 1 5D359AA2
P 9250 3850
F 0 "#PWR0104" H 9250 3700 50  0001 C CNN
F 1 "+3.3V" V 9265 3978 50  0000 L CNN
F 2 "" H 9250 3850 50  0001 C CNN
F 3 "" H 9250 3850 50  0001 C CNN
	1    9250 3850
	0    -1   -1   0   
$EndComp
$Comp
L power:+5V #PWR0105
U 1 1 5D359AA8
P 9250 3650
F 0 "#PWR0105" H 9250 3500 50  0001 C CNN
F 1 "+5V" V 9265 3778 50  0000 L CNN
F 2 "" H 9250 3650 50  0001 C CNN
F 3 "" H 9250 3650 50  0001 C CNN
	1    9250 3650
	0    -1   -1   0   
$EndComp
Text Label 9250 1950 2    50   ~ 0
But0
Text Label 9250 2050 2    50   ~ 0
But1
Text Label 9250 2150 2    50   ~ 0
But2
Text Label 9250 2250 2    50   ~ 0
But3
$Comp
L Switch:SW_Push SW4
U 1 1 5D35D4DD
P 8300 2700
F 0 "SW4" H 8450 2850 50  0000 C CNN
F 1 "SW_Push" H 8300 2894 50  0001 C CNN
F 2 "" H 8300 2900 50  0001 C CNN
F 3 "~" H 8300 2900 50  0001 C CNN
	1    8300 2700
	0    1    1    0   
$EndComp
$Comp
L Device:R R18
U 1 1 5D35D948
P 8300 1650
F 0 "R18" H 8370 1696 50  0000 L CNN
F 1 "4k7" H 8370 1605 50  0000 L CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 8230 1650 50  0001 C CNN
F 3 "~" H 8300 1650 50  0001 C CNN
	1    8300 1650
	1    0    0    -1  
$EndComp
$Comp
L Device:R R17
U 1 1 5D35E330
P 7950 1650
F 0 "R17" H 8020 1696 50  0000 L CNN
F 1 "4k7" H 8020 1605 50  0000 L CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 7880 1650 50  0001 C CNN
F 3 "~" H 7950 1650 50  0001 C CNN
	1    7950 1650
	1    0    0    -1  
$EndComp
$Comp
L Device:R R19
U 1 1 5D35E57C
P 8650 1650
F 0 "R19" H 8720 1696 50  0000 L CNN
F 1 "4k7" H 8720 1605 50  0000 L CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 8580 1650 50  0001 C CNN
F 3 "~" H 8650 1650 50  0001 C CNN
	1    8650 1650
	1    0    0    -1  
$EndComp
$Comp
L Device:R R16
U 1 1 5D35E79E
P 7600 1650
F 0 "R16" H 7670 1696 50  0000 L CNN
F 1 "4k7" H 7670 1605 50  0000 L CNN
F 2 "Resistor_THT:R_Axial_DIN0207_L6.3mm_D2.5mm_P10.16mm_Horizontal" V 7530 1650 50  0001 C CNN
F 3 "~" H 7600 1650 50  0001 C CNN
	1    7600 1650
	1    0    0    -1  
$EndComp
$Comp
L Switch:SW_Push SW5
U 1 1 5D35E9D9
P 8650 2700
F 0 "SW5" H 8800 2850 50  0000 C CNN
F 1 "SW_Push" H 8650 2894 50  0001 C CNN
F 2 "" H 8650 2900 50  0001 C CNN
F 3 "~" H 8650 2900 50  0001 C CNN
	1    8650 2700
	0    1    1    0   
$EndComp
$Comp
L Switch:SW_Push SW3
U 1 1 5D35ED38
P 7950 2700
F 0 "SW3" H 8100 2850 50  0000 C CNN
F 1 "SW_Push" H 7950 2894 50  0001 C CNN
F 2 "" H 7950 2900 50  0001 C CNN
F 3 "~" H 7950 2900 50  0001 C CNN
	1    7950 2700
	0    1    1    0   
$EndComp
$Comp
L Switch:SW_Push SW2
U 1 1 5D35EFDA
P 7600 2700
F 0 "SW2" H 7750 2850 50  0000 C CNN
F 1 "SW_Push" H 7600 2894 50  0001 C CNN
F 2 "" H 7600 2900 50  0001 C CNN
F 3 "~" H 7600 2900 50  0001 C CNN
	1    7600 2700
	0    1    1    0   
$EndComp
Wire Wire Line
	7600 1950 7600 1800
Wire Wire Line
	7600 1950 9250 1950
Wire Wire Line
	7600 1950 7600 2500
Connection ~ 7600 1950
Wire Wire Line
	7950 2500 7950 2050
Wire Wire Line
	7950 2050 9250 2050
Connection ~ 7950 2050
Wire Wire Line
	7950 2050 7950 1800
Wire Wire Line
	8300 1800 8300 2150
Wire Wire Line
	8300 2150 9250 2150
Connection ~ 8300 2150
Wire Wire Line
	8300 2150 8300 2500
Wire Wire Line
	8650 1800 8650 2250
Wire Wire Line
	8650 2250 9250 2250
Connection ~ 8650 2250
Wire Wire Line
	8650 2250 8650 2500
Wire Wire Line
	7600 2900 7600 3050
Wire Wire Line
	7600 3050 7950 3050
Wire Wire Line
	8650 3050 8650 2900
Wire Wire Line
	8300 2900 8300 3050
Connection ~ 8300 3050
Wire Wire Line
	8300 3050 8650 3050
Wire Wire Line
	7950 2900 7950 3050
Connection ~ 7950 3050
Wire Wire Line
	7950 3050 8300 3050
Wire Wire Line
	7600 1500 7600 1350
Wire Wire Line
	7600 1350 7950 1350
Wire Wire Line
	8650 1350 8650 1500
Wire Wire Line
	8300 1500 8300 1350
Connection ~ 8300 1350
Wire Wire Line
	8300 1350 8650 1350
Wire Wire Line
	7950 1500 7950 1350
Connection ~ 7950 1350
Wire Wire Line
	7950 1350 8300 1350
$Comp
L power:+3.3V #PWR020
U 1 1 5D386E5C
P 8650 1350
F 0 "#PWR020" H 8650 1200 50  0001 C CNN
F 1 "+3.3V" V 8665 1478 50  0000 L CNN
F 2 "" H 8650 1350 50  0001 C CNN
F 3 "" H 8650 1350 50  0001 C CNN
	1    8650 1350
	1    0    0    -1  
$EndComp
Connection ~ 8650 1350
$Comp
L power:GND #PWR021
U 1 1 5D38750B
P 8650 3050
F 0 "#PWR021" H 8650 2800 50  0001 C CNN
F 1 "GND" H 8655 2877 50  0001 C CNN
F 2 "" H 8650 3050 50  0001 C CNN
F 3 "" H 8650 3050 50  0001 C CNN
	1    8650 3050
	1    0    0    -1  
$EndComp
Connection ~ 8650 3050
Text Notes 7600 3700 0    50   ~ 0
Simplified buttons connection\n!!! Use for quick prototype only !!!\nIn this way you won't be able\nto add lamps later
$EndSCHEMATC
