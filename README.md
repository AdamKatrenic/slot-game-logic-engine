# Java Slot Logic Engine (v1.1)

Profesionálne spracovaný backendový engine pre výherný automat (Slot Machine) napísaný v **Jave 25**. Projekt demonštruje prácu s RNG, spracovanie komplexnej hernej logiky a verifikáciu dát pomocou Monte Carlo simulácie.

## Kľúčové vlastnosti
* **Grid 3x5 & 5 Paylines:** Podpora pre fixné výherné línie s prísnou logikou zľava doprava.
* **Symbol Weights:** Pravdepodobnosť výhier riadená váhami v Enumoch pre presné nastavenie matematického modelu.
* **Special Symbols:** Implementácia **WILD** (nahrádza symboly) a **SCATTER** (spúšťa Free Spins).
* **Monte Carlo Simulator:** Nástroj na overenie RTP (Return to Player) spracovaním 1 000 000 spinov pod 400 ms.
* **Unit Tests:** Sada testov (JUnit 5) pokrývajúca výherné scenáre a prerušenia línií.

## Matematický Model
Projekt bol validovaný simuláciou milióna hier s nasledujúcim výsledkom:
* **Namerané RTP:** ~85,06%
* **Volatilita:** Stredná
* **Výkon:** ~2.6 milióna spinov/sekundu

## Použité technológie
* **Java 25** (OpenJDK)
* **Maven** (správa závislostí)
* **JUnit 5** (automatizované testovanie)

## Ako spustiť
1. **Hra v konzole:** Spustite `src/main/java/sk/adamkatrenic/Main.java`
2. **RTP Simulátor:** Spustite `src/main/java/sk/adamkatrenic/logic/RtpSimulator.java`
3. **Testy:** Spustite `mvn test` alebo v IDE cez JUnit runner.

---
*Vytvorené ako ukážka programátorských a analytických schopností pre pozíciu Java Junior.*