基础类型
---
Java中八大基础类型

首先看下述表格，主要属性均在表中展示。

类型|内存长度|范围|包装类型
|---|---|---|---
byte|1byte（8bit,1个符号位）|&nbsp;|Byte
short|2byte（16bit,1个符号位）|&nbsp;|Short
int|4byte（32bit,1个符号位）|&nbsp;|Integer
long|8byte（64bit,1个符号位）|&nbsp;|Long
char|2byte（16bit,无符号位）|&nbsp;|Character
float|4byte|&nbsp;|Float
double|8byte|&nbsp;|Double
boolean|--|--|Boolean
 
 可以从上述的表格中，总结出几点：

 同时又提出几点疑问：
 1. boolean类型在内存中，所占空间是多少？
 2. 基础类型和包装类型到底是如何转化的？
 3. 基础类型和包装类运用在表达式上，是怎么计算的？
 4. float和double类型的精度到底该如何理解？
 
 boolean类型特别说明：boolean类型理论上只需要