.include "./cs47_macro.asm"

.data
msg1: .asciiz "Enter a +ve number : "
msg2: .asciiz "Enter another +ve number : "
msg3: .asciiz "LCM of "
s_is: .asciiz "is"
s_and: .asciiz "and"
s_space: .asciiz " "
s_cr: .asciiz "\n"

.text
.globl main
main:
	print_str(msg1)
	read_int($s0)
	print_str(msg2)
	read_int($s1)
	
	move $v0, $zero
	move $a0, $s0
	move $a1, $s1
	move $a2, $s0
	move $a3, $s1
	jal  lcm_recursive
	move $s3, $v0
	
	print_str(msg3)
	print_reg_int($s0)
	print_str(s_space)
	print_str(s_and)
	print_str(s_space)
	print_reg_int($s1)
	print_str(s_space)
	print_str(s_is)
	print_str(s_space)
	print_reg_int($s3)
	print_str(s_cr)
	exit

#------------------------------------------------------------------------------
# Function: lcm_recursive 
# Argument:
#	$a0 : +ve integer number m
#       $a1 : +ve integer number n
#       $a2 : temporary LCM by increamenting m, initial is m
#       $a3 : temporary LCM by increamenting n, initial is n
# Returns
#	$v0 : lcm of m,n 
#
# Purpose: Implementing LCM function using recursive call.
# 
#------------------------------------------------------------------------------
lcm_recursive:
	# Store frame
	addi $sp, $sp, -28
	sw $fp, 28($sp)
	sw $ra, 24($sp)
	sw $a0, 20($sp) #a0 = m
	sw $a1, 16($sp) #a1 = n
	sw $a2, 12 ($sp) #$a2 = LCM_m
	sw $a3, 8 ($sp) #a3 = LCM_n
	addi $fp, $sp, 28

	# Body
body:
	beq $a2, $a3, return #Base case, if lcm_m == lcm_n, return lcm_m in $v0
	bgt $a2, $a3, case2 #Second case, if lcm_m > lcm_n, we will do lcm_n = lcm_n + n
	add $a2, $a2, $a0 #lcm_m = lcm_m + m
	j lcm_recursive #Jumps back to procedure
case2: add $a3, $a3, $a1 #lcm_n = lcm_n + n
       j body #jump back to the body, we have not met our return case yet
return: add $v0, $v0, $a2 #Adds the value of $a2 (LCM_m) to $v0 (our return value)


	# Restore frame
	lw $fp, 28($sp)
	lw $ra, 24($sp)
	lw $a0, 20($sp)
	lw $a1, 16($sp)
	lw $a2, 12 ($sp)
	lw $a3, 8 ($sp)
	addi $sp, $sp, 28

	jr $ra
	
