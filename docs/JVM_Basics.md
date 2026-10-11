## JVM BASICS

# JDK
**Java Development Kit** = JRE + developer tools such as `javac` (compiler) and `jar`, debugger. You need the JDK to *write and compile* Java.

# JRE
**Java Runtime Engine** = JVM + the standard class libraries needed to *run* Java programs.

# JVM
**Java Virtual Machine** = runs compiled Java programs. It reads bytecode and executes it on the current operating system.

# bytecode
When you compile a `.java` file with `javac`, you do not get machine code. You get a `.class` file containing **bytecode**, a compact, platform-independent set of instructions. 
The JVM translates this bytecode into machine instructions for the computer it runs on.

# write once, run anywhere
Because Java compiles to bytecode instead of OS-specific machine code, the same `.class` files can run on Windows, macOS or Linux, as long as a JVM is installed there.

Each platform has its own JVM that understands the same bytecode, so developers do not need to rewrite or recompile their program for every operating system.
