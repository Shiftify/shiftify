# Šichtovník (Shiftify)

Šichtovník je projekt na předmět BI-SP2 na FIT ČVUT. 
Jedná se o android aplikaci, která umožňuje spravovat lidi, jejich šichty a zobrazovat si, kdy má daný člověk volno či je v práci. Dále pak aplikace umí ukázat předhled obsazeností (volno / je v prácí) lidí v daný datum.

Aplikace má za cíl zlepšít svolávání týmu dobrovolných hasičů k akci.


## Autoři

- Lukáš Komárek
- Ilia Liferov
- Ondřej Marek
- Jiří Borský

### Bývalí autoři
- Vojtěch Mach
- Petr Panský




## Chcete vyzkoušet demo verzi?

Pokud si chcete u sebe na mobilu vyzkoušet demo naší aplikace, neváhejte. Vše ohledně instalace najdete na naší wiki v sekci [Instalace demo verze](https://github.com/thepetas/shiftify/wiki/Instalace-demo-verze).


## Zprovoznění projektu

Nejprve je nutné si nainstalovat [Java Platform (JDK)](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).

Pokud používáte Windows, musíte si poté vytvořit proměnou prostředí. Pravým tlačítkem myši klikněte na *Tento počítač* a vyberte *Vlastnosti*. Tam vlevo vyberte *Upřesnit nastavení systému* a v okénku, které se zobrazí, vyberte dole *Proměnné prostředí*. Pokud zde nenajdete *JAVA_HOME*, vyberte možnost *Nová...*, *JAVA_HOME* napište do názvu a do hodnoty napište odkaz k složce bin defaultně istalované na *C:\Program Files\Java\jdk1.8.0_111* (může se lišit podle upřesnění instalace a verze jdk, kterou máte). Klikněte na *OK* a můžete vše zavřít.

Nyní můžete bez problémů nainstalovat [Android Studio](https://developer.android.com/studio/index.html).

Po nainstalování otevřete a vyberte otevřít projekt. Najděte složku, ve které je projekt a vyberte ji. Zde nahoře kladívkem zbuildíte a trojúhelníčkem vedle něj spustíte projekt.

Testy najdete ve složkách *cz.cvut.fit.shiftify*/java/\*(test)* a spustíte je pravým kliknutím na soubory jednotlivě, nebo na složku a vybráním možnosti *Run ...* v případě jednotlivého souboru nebo *Run Tests in ...* v případě složky.
