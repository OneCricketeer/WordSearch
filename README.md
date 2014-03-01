WordSearch
==========
Multithreaded WordSearch

#### Overview:  
Initially created for Project Euler's Problem 11 of finding the maximum product from a 20x20 grid of numbers, I coded the problem so abstractly, that I went ahead and adapted my solution into a WordSearcher. The multithreaded part came in later, since I wanted to quickly scan multiple directions at a time. The output below of the 50 United States ran in about 40ms on my computer. I haven't ran any official speed tests on this, but it should be able to cover a reasonable sized square grid in less than a minute.  

#### Description:  
This project reads-in 2 files. The wordlist contains all the words that you want to find in the wordsearch, the words are not case-sensitive. The wordsearch grid itself is essentially what you would expect a wordsearch to look like with space separated characters, again non-casesensitive. There are 4 different directions a scanner can scan: horizonal, vertical, and the two diagonals. In this implementaion, wrap-around words are not possible. Each scanner is initialized with a finite set of data to scan over before continuing. This allows finding exact subsets of the grid where specific strings of letters are found. Once full, a scanner will process its data, then empty its contents and continue scanning. For the wordsearch, for example, it will check if the word it scanned is in the wordlist either forwards or backwards ignoring case. If so, then the location of the word occurence is noted, as seen in the output below. The first letter that triggered the find and the direction the word occurred are also printed to help isolate the word in the grid.  

Running the program
===================
The following code gets output similar to that below: 
```java
WordSearch ws = new WordSearch("WordSearchGrid.txt", "WordSearchList.txt");
CharScannerFactory scanFact = new CharScannerFactory();

Character[][] grid = ws.getGrid();
scanFact.setGrid(grid);

ArrayList<CharScanner> scanPool = new ArrayList<CharScanner>();

int min_size = ws.getScannerLength()[0];
int max_size = ws.getScannerLength()[1];

for (int i = min_size; i <= max_size; i++) {
  scanFact.setScannerSize(i);
  scanPool.add(fact.getRightDiagScanner());
  scanPool.add(fact.getLeftDiagScanner());
  scanPool.add(fact.getVerticalScanner());
  scanPool.add(fact.getHorizontalScanner());
}

for (CharScanner sc : scanPool) {
  sc.setWordList(ws.getWordList());
}

System.out.println("Location Letter Direction Word");
System.out.println("----------------------------------");

for (int j = 0; j < scanPool.size(); j++) {
  scanPool.get(j).start();
}
```


Sample Input
============
```
S T E W L H B S L J Z T Z Z Y U W P F T C A L I F O R N I A         TEXAS  
I P P I S S I S S I M A E Y U E I P C Z M N S I O N I L L I         OHIO  
Q S L E U B Q Y Z A L S Y V W O V E K Q S A S N A K R A C G         CALIFORNIA  
G K M V F B Z Y N R N Z V A Z L U N U P H K Q L O R P J X R         OREGON  
Q Q B K H X Y E N E V A D A Y B U N B I A P R Y A F Y M T R         NEWYORK  
M H A S X G W S V A R I Z O N A Q S B A I N X D O R D S R V         COLORADO  
E J U W S Y S R M A K S A L A H O Y S N N K A G A Q O C C P         NORTHCAROLINA  
X Z L V O M A E A T O S E N N I M L A G I L E I C W W V G I         PENNSYLVANIA  
E C D R L I I J K P Y E M J K M X V I V G X E N D I V H X N         WISCONSIN  
R X K A M D V W P E O F F I F I G A G A R L T S S N N D V P         MAINE  
I P N I G S P E S Y N O U M K B Y N R R I C X C A N I V D Y         CONNECTICUT  
H S L N J I Q N E O D T K D J N S I O X V E O E I S D J E N         ARIZONA  
S B X I P Z D N Q M U H U D B T D A E N T N E L D I N L W X         GEORGIA  
P I W G J A G A N R I T S C T G O F G S S C T S Q S D A A U         IDAHO  
M K Y R N I Q O H V Z S H E K I I M P I E K D M S U P B K B         TENNESSEE  
A S O I M E G G N O Y Y S C H Y O Y N B W Q G A E E T F H F         NEBRASKA  
H O M V I E B T A H L U T O A N I L O R A C H T R O N A R M         MICHIGAN  
W U I O R B J R R M H O R U U R B A G H F S R R E J G N H I         ILLINOIS  
E T N O N A R F A C A O U M C R O J T N W D B B U Q Q N E K         IOWA  
N H G R M C N H A S M B K I U I I L F N E V F P Z R Z O P T         INDIANA  
H D B I B N B S O C K A A L S G T O I X O W Y M H F X R E J         MONTANA  
A A Y U P I S O C D T A R L A I J C O N R M M F H H B T H Y         MASSACHUSETTS  
D K L Y R A A O C I E I A Y A H A U E D A W R E N I Z H M B         LOUISIANA  
I O S P M G L J S C X I H N L Y O N E N W F W E X T G D O Q         VIRGINIA  
R T P N S O E W M J A E S E J A L M A K N V Z V V I B A N O         NEWHAMPSHIRE  
O A G Q R Y I J F A S C F L Q O N A A U W O B U U H C K T B         OKLAHOMA  
L V G A I I A W A H I X T N A G M D H M D P C O N L X O A N         UTAH  
F E D A Z Q X Y K R C N O T G N I H S A W H M P I C P T N Z         RHODEISLAND  
O O D E L A W A R E B V E B J T D M D U O X A Q N E L A A R         MISSOURI  
R A T Z K N M I C H I G A N L Z W S A U X Z W T O U Z L K B         NORTHDAKOTA  
                                                                    WASHINGTON  
                                                                    DELAWARE  
                                                                    MISSISSIPPI  
                                                                    SOUTHDAKOTA  
                                                                    ALASKA  
                                                                    WESTVIRGINIA  
                                                                    ARKANSAS  
                                                                    KANSAS  
                                                                    WYOMING  
                                                                    VERMONT  
                                                                    MARYLAND  
                                                                    NEWMEXICO  
                                                                    NEVADA  
                                                                    KENTUCKY  
                                                                    ALABAMA  
                                                                    MINNESOTA  
                                                                    HAWAII  
                                                                    SOUTHCAROLINA  
                                                                    NEWJERSEY  
                                                                    FLORIDA  
```

Sample Output
=============
```
Location Letter Direction Word
----------------------------------
[09, 01]   e      |       NEWHAMPSHIRE
[10, 03]   k      /       NEWYORK
[01, 21]   c      --      CALIFORNIA
[16, 02]   s      |       SOUTHDAKOTA
[22, 01]   a      |       FLORIDA
[02, 01]   i      --      MISSISSIPPI
[02, 23]   s      --      ILLINOIS
[03, 21]   s      --      ARKANSAS
[19, 07]   r      \       RHODEISLAND
[25, 09]   m      \       MAINE
[10, 04]   a      |       VIRGINIA
[14, 03]   w      |       WYOMING
[17, 09]   a      \       ALABAMA
[15, 05]   n      \       NEBRASKA
[03, 21]   s      --      KANSAS
[04, 08]   y      |       NEWJERSEY
[24, 05]   m      /       MASSACHUSETTS
[20, 11]   m      \       MARYLAND
[19, 04]   o      /       OREGON
[05, 09]   n      --      NEVADA
[06, 10]   a      --      ARIZONA
[17, 13]   t      \       CONNECTICUT
[11, 09]   s      \       SOUTHCAROLINA
[19, 12]   o      \       OKLAHOMA
[17, 11]   l      \       LOUISIANA
[12, 06]   i      \       IDAHO
[08, 09]   a      --      MINNESOTA
[07, 10]   a      --      ALASKA
[29, 02]   o      /       COLORADO
[06, 03]   a      \       IOWA
[13, 10]   m      \       MISSOURI
[19, 19]   t      \       VERMONT
[22, 11]   t      |       TEXAS
[17, 15]   a      --      NORTHCAROLINA
[19, 20]   n      \       NEWMEXICO
[09, 09]   k      \       KENTUCKY
[16, 19]   n      /       WISCONSIN
[02, 18]   p      |       PENNSYLVANIA
[17, 14]   o      /       OHIO
[05, 21]   a      |       WESTVIRGINIA
[12, 22]   e      \       TENNESSEE
[08, 19]   a      |       GEORGIA
[19, 28]   n      |       NORTHDAKOTA
[28, 12]   n      --      WASHINGTON
[05, 21]   a      \       INDIANA
[15, 26]   u      \       UTAH
[29, 03]   d      --      DELAWARE
[30, 07]   m      --      MICHIGAN
[27, 05]   i      --      HAWAII
[23, 29]   m      |       MONTANA
```
