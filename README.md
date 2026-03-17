# Java Slot Logic Engine (v1.1)

[![Java Version](https://img.shields.io/badge/Java-25-orange.svg)](https://openjdk.org/)
[![Build Status](https://img.shields.io/badge/Tests-Passing-brightgreen.svg)](https://junit.org/junit5/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Profesionálne orientovaný backendový engine pre výherné automaty, vyvinutý s dôrazom na **vysoký výkon**, **matematickú presnosť** a **oddelenie hernej logiky od dát**. Projekt demonštruje pokročilé techniky v Jave a hlboké porozumenie hernej matematiky (RTP, Volatilita, Hit Rate).

---

## Kľúčové Inovácie

* **Weighted Random Selection:** Implementácia RNG založená na váženom rozdelení, čo umožňuje presnú kontrolu nad vzácnosťou jednotlivých symbolov.
* **Dynamic Payout Matrix:** Algoritmus pre výpočet výhier dynamicky škáluje odmeny podľa dĺžky línie (3, 4 alebo 5 symbolov).
* **WILD Substitution Intelligence:** Sofistikovaná logika, ktorá pri substitúcii žolíkom (WILD) automaticky identifikuje najhodnotnejšiu možnú kombináciu na línii.
* **High-Speed Simulation:** Engine je optimalizovaný pre miliónové simulácie, čo je nevyhnutné pre certifikačné procesy (GLI štandardy).



---

## Matematický Report (Monte Carlo Analysis)

Validácia systému prebehla na vzorke **1 000 000 spinov**. Výsledky potvrdzujú stabilitu a férovosť matematického modelu:

| Parameter | Hodnota | Technická poznámka |
| :--- | :--- | :--- |
| **Základné RTP (Base Game)** | **84.14 %** | Teoretický výpočet cez `ParSheetGenerator` |
| **Celkové RTP (s Bonusmi)** | **~94.30 %** | Namerané Monte Carlo simuláciou |
| **Bonus Hit Rate** | **1 : 80.3** | Pravdepodobnosť aktivácie Free Spins (3x Scatter) |
| **Max Win (Single Line)** | **166.6 €** | Pri stávke 1.0 € (5x SEVEN) |
| **Výpočtový výkon** | **2.5M+ spin/s** | Testované na OpenJDK 25 |



---

## Štruktúra a Použité Technológie

* **Java 25:** Využitie moderných prvkov jazyka pre maximálnu efektivitu.
* **Maven:** Správa závislostí a build lifecycle.
* **JUnit 5:** 100% pokrytie kritickej logiky (WILD substitúcia, Scatters, Paylines).
* **Properties Config:** Externý súbor `config.properties` pre konfiguráciu bez nutnosti rekompilácie.

---

## Verifikácia a Testovanie

Projekt obsahuje komplexnú sadu unit testov, ktoré simulujú špecifické mriežky (Mock Grids) na overenie správnosti výpočtov.

```bash
# Spustenie sady testov integrity
mvn test