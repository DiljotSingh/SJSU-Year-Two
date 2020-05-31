.include "./cs47_macro.asm"

.data
msg1: .asciiz "Enter a number ? "
msg2: .asciiz "Factorial of the number is "
charCR: .asciiz "\n"

.text
.globl main
main:	print_str(msg1)
	read_int($t0)
	
# Write body of the iterative
# factorial program here
# Store the factorial result into 
# register $s0

	addi $s1, $zero, 1 #Intialize our loop controller to 1 (i.e. i = 1)
	addi $s2, $zero, 1 #Intitalize our product that we will eventually store into $s0 to 1
	
while:	bgt $s1, $t0, exit #If i is greater than the input number (stored in $t0), we are done (i.e. i > N)
	mul $s2, $s2, $s1 #Multiply our product by our loop controller (i.e. product = product * i)
	addi $s1, $s1, 1 # Increments our loop controller by one, (i.e. i++)
	j while #Jumps to our while statement to keep looping until our loop controller is greater than the input number ($s1 > $t0)
	
exit: 	add $s0, $s0, $s2 #Adds our product ($s2) to our $s0 register, storing the calculation in $s0



# DON'T IMPLEMENT RECURSIVE ROUTINE 
# WE NEED AN ITERATIVE IMPLEMENTATION 
# RIGHT AT THIS POSITION. 
# DONT USE 'jal' AS IN PROCEDURAL /
# RECURSIVE IMPLEMENTATION.
	
	print_str(msg2)
	print_reg_int($s0)
	print_str(charCR)
	
	exit
	
