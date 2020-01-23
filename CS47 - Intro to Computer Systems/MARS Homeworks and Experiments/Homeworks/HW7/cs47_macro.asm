#<-----------------------MACRO DEFINITIONS ------------------------------>
	#Macro: print_str
	#Usage: print_string(<address of the string>)
	.macro print_str($arg)
	li $v0, 4 # System call co de for print_str
	la $a0, $arg #Address of the string to print
	syscall #Print the string
	.end_macro 
	
	#Macro : print_int
	#Usage : print_int(<val>)
	.macro print_int($arg)
	li $v0, 1 # System call code for print_int
	li $a0, $arg # Integer to print
	syscall #Print the integer
	.end_macro 
	
	#Macro: exit
	#Usage: exit
	.macro exit
	li $v0, 10
	syscall
	.end_macro 
	
	#Macro : read_int
	#Usage : read_int(<register>)
	.macro read_int($reg)
	li $v0, 5 #System call code for read_int
	syscall #Reads the integer
	move $reg, $v0	#Move the integer into the target register
	.end_macro 
	
	#Macro : print_reg_int
	#Usage: print_reg_int(<register>)
	.macro print_reg_int($reg)
	li $v0, 1 #System call code for print_int
	move $a0, $reg #Move the register value to $a0
	syscall #Print the integer
	.end_macro 
	
	#Macro: swap_hi_lo
	#Usage: swap_hi_lo(<$temporary1, $temporary2>)
	#Purpose: Swaps the values in the Hi and Lo registers
	.macro swap_hi_lo($temp1, $temp2)
	mfhi $temp1 #Store the Hi value into $temp1 register
	mflo $temp2 #Store the Lo value into $temp2 register
	mthi $temp2 #Move the value from $temp2 (Lo) into Hi
	mtlo $temp1 #Move the value from $temp1 (Hi) into Lo
	.end_macro
	
	#Macro: print_hi_lo
	#Usage: print_hi_lo(<$strHi, $strEqual, $strComma, $strLo>)
	#Purpose: prints the contents of the Hi and Lo registers in a more readable format
	.macro print_hi_lo($strHi, $strEqual, $strComma, $strLo)
	mfhi $t0 #Store the Hi value into $t0
	mflo $t1 #Store the Lo value into $t1
	print_str($strHi) #Prints "Hi"
	print_str($strEqual) #Prints "="
	print_reg_int($t0) #Prints value of Hi register (stored in $t0)
	print_str($strComma) #Prints ","
	print_str($strLo) #Prints "Lo"
	print_str($strEqual) #Prints "="
	print_reg_int($t1) #Prints value of Lo register (stored in $t1)
	.end_macro 
	
	#Macro: lwi
	#Usage: lwi(<register, upper immediate value, lower immediate value>)
	#Purpose: Takes two 16-bit immediate values and puts them to higher and lower bits of $reg, respectively
	.macro lwi($reg, $ui, $li) 
	lui $reg, $ui #Loads the upper half of the register with the $ui (upper immediate) value and sets low-order 16 bits to 0
	ori $reg, $li #Loads the lower half of the register with the $li (lower immediate) value
	.end_macro
	
	#Macro: push
	#Usage: push(<register>)
	#Purpose: Pushes data from argument register ($reg) into stack
	.macro push($reg)
	sw $reg, 0x0($sp) #Stores the argument register ($reg) data into the stack pointer
	addi $sp, $sp, -4 #Decreases stack pointer by 4 (since we wrote 4 bytes of data)
	.end_macro
	
	#Macro: pop
	#Usage: pop(<register>)
	#Purpose: Pops data from stack and copies it into argument register
	.macro pop($reg)
	addi $sp, $sp, 4 #Increase stack pointer by 4 (since we are making 4 bytes of space writable)
	lw $reg, 0x0($sp) #Loads the data that we popped from the stack into the argument register ($reg)
	.end_macro
	
	
	
	
	
