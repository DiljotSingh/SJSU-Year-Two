.include "./cs47_proj_macro.asm"
.text
.globl au_normal
# TBD: Complete your project procedures
# Needed skeleton is given
#####################################################################
# Implement au_normal
# Argument:
# 	$a0: First number
#	$a1: Second number
#	$a2: operation code ('+':add, '-':sub, '*':mul, '/':div)
# Return:
#	$v0: ($a0+$a1) | ($a0-$a1) | ($a0*$a1):LO | ($a0 / $a1)
# 	$v1: ($a0 * $a1):HI | ($a0 % $a1) 
# Notes:
#####################################################################
au_normal:
# TBD: Complete it

	#Store the frame
	store_frame #Macro call to store frame
	
	#Operand is in ASCII code
	beq $a2, '+', add_procedure #If operand is '+', this is an addition operation
	beq $a2, '-', sub_procedure #If operand is '-' this is a subtraction operation
	beq $a2, '*', mul_procedure #If operand is a '*', this is a multiplication operation
	beq $a2, '/', div_procedure #If operand is a '/', this is a division operation
	j done #If operand is none of above

#Addition procedure, adds $a0 and $a1
#Return value is in $v0
add_procedure:
store_frame #Macro call to store frame
add $v0, $a0, $a1 #$v0 = $a0 + $a1
restore_frame #Macro call to restore frame
j done #Done

#Subtraction procedure, subtracts $a1 from $a0
#Return value is in $v0
sub_procedure:
store_frame #Macro call to store frame
sub $v0, $a0, $a1 #$v0 = $a0 - $a1
restore_frame #Macro call to restore frame
j done #Done

#Multiplication procedure, multiplies $a0 and $a1
#Return value: LO is in $v0 and HI is in $v1
mul_procedure:
store_frame #Macro call to store frame
mult $a0, $a1 #Multiplies $a0 and $a1, setting HI to high-order 32 bits and LO to low-order 32 bits of the product
mflo $v0 #For multiplication, $v0 will contain LO
mfhi $v1 #For multiplication, $v1 will contain HI
restore_frame #Macro call to restore frame
j done

#Division procedure, divides $a0 by $a1
#Return value: LO is in $v0 and HI is in $v1
div_procedure:
store_frame #Macro call to store frame
div $a0, $a1 #Divides $a0 by $a1, storing remainder in HI and quotient in LO
mflo $v0 #For division, $v0 will contain LO register (the quotient)
mfhi $v1 #For division, $v1 will contain HI register (the remainder)
restore_frame #Macro call to restore frame
j done
 
done:
	#Restore frame
	restore_frame #Macro call to restore frame and jump to $ra
