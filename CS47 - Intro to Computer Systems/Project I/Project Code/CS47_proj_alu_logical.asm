.include "./cs47_proj_macro.asm"
.text
.globl au_logical
# TBD: Complete your project procedures
# Needed skeleton is given
#####################################################################
# Implement au_logical
# Argument:
# 	$a0: First number
#	$a1: Second number
#	$a2: operation code ('+':add, '-':sub, '*':mul, '/':div)
# Return:
#	$v0: ($a0+$a1) | ($a0-$a1) | ($a0*$a1):LO | ($a0 / $a1)
# 	$v1: ($a0 * $a1):HI | ($a0 % $a1)
# Notes:
#####################################################################
au_logical:
store_frame	#Macro call to store frame

	beq $a2, '+', add_logical #If operand is '+', this is an addition operation
	beq $a2, '-', sub_logical #If operand is '-', this is a subtraction operation
	beq $a2, '*', mul_logical #If operand is '*', this is a multplication operation
	beq $a2, '/', div_logical #If operand is '/', this is a division operation
	j done #Goes to done if operand is none of above

#+++++++++++++++++++++++++++++ADDITION LOGICAL+++++++++++++++++++++++++++++++++++
#Procedure: add_logical
#Usage: Computes $a0 + $a1, returning the sum in $v0
#Arguments: $a0 - first number, $a1 - second number
#Calls add_sub_logical with $a2 = 0x00000000 (addition mode)
add_logical:
	store_frame		#Macro call to store frame
	li $a2, 0x00000000	#Sets mode to 0x00000000 (addition)
	jal add_sub_logical    #Calls the common  add_sub_logical procedure
	restore_frame		#Macro call to restore frame


#-------------------------- SUBTRACTION LOGICAL-------------------------
#Procedure: sub_logical
#Usage: Computes $a0 - $a1, returning the total in $v0
#Arguments: $a0 - first number, $a1 - second number
#Calls add_sub_logical with $a2 = 0xFFFFFFFF (subtraction mode)
sub_logical:
	store_frame		#Macro call to store frame
	li $a2, 0xFFFFFFFF     #Sets mode to 0xFFFFFFFF (subtraction)
	jal add_sub_logical	#Calls the common add_sub_logical procedure
	restore_frame		#Macro call to restore frame
	
#**************************MULTIPLICATION LOGICAL ****************************************
#Procedure: mul_logical
#Usage: Computes $a0 x $a1
#$a0 is the multiplicand
#$a1 is the multiplier
#Stores Hi in $v1, Lo in $v0
mul_logical:
	store_frame				#Macro call to store frame
	jal twos_complement_if_neg		#Calls utility procedure to account for signed multiplication
	move $s0, $v0				#Moves $v0 from previous procedure call into $s0
	move $a0, $a1				#Stores $a1 in $a0 to compute its two's complement if it negative (next call)
	jal twos_complement_if_neg		#Calls utility procedure to acocount for signed multiplication
	move $a0, $s0				#Moves $s0 back to $a0
	move $a1, $v0				#Moves $v0 back to $a1
	j mul_unsigned			#Calls the mul_unsigned procedure
mul_done:
	restore_frame				#Macro call to restore frame
	
#///////////////////////// DIVISION LOGICAL PROCEDURE//////////////////////////////////////////
#Procedure: div_logical
#Usage: Computes $a0/ $a1
#Arguments: $a0 is dividend, $a1 is divisor
#Returns quotient in $v0, remainder in $v1
div_logical:
	store_frame				#Macro call to store frame
	jal twos_complement_if_neg		#Gets complement of $a0 if negative
	move $s0, $v0				#Saves that complement in $s0
	move $a0, $a1				#Sets $a0 to $a1 for next complement check
	jal twos_complement_if_neg		#Gets complement of $a1 if negative
	move $a0, $s0				#Restores complement of $a0 to $a0
	move $a1, $v0				#Sets $a1 to its complement
	j div_unsigned				#Jumps to div procedure
div_done:
	restore_frame

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`UTILITY PROCEDURES~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

#Procedure: add_sub_logical
#Usage: Returns $a0 + $a1 in $v0 if in addition mode, $a0 - $a1 in $v0 if in subtraction mode
#Arguments: $a0 - first number, $a1 - second number, $a2 - Mode (0x00000000 is addition, 0xFFFFFFFF is subtraction)

add_sub_logical:
store_frame #Macro call to store frame

add $t0, $zero, $zero  	#int i = 0
add $s0, $zero, $zero 		#int sum =0
extract_nth_bit($s1,$a2,$zero)	#C($s1) = $a2[0]
beq $s1, 1, subtraction  #If LSB is 1, then this means the mode is subtraction
beq $s1, 0, addition  #Otherwise, if the LSB is 0, then the mode is addition

subtraction:
not $a1, $a1 #$a1 = ~$a1, negates $a1 for subtraction then continues to addition as normal

addition:
#Goal: Y is one bit full addition result of ($a0[i] XOR $a1[i]) XOR (C)
extract_nth_bit ($t1, $a0, $t0) # $t1 = $a0[i]
extract_nth_bit($t2, $a1, $t0) #$t2 = $a1[i]
xor  $t3,  $t1, $t2  #$t3 = $a0[i] XOR $a1[i]
xor $t4, $t3, $s1 	#Y = $t4 => C XOR ($a0[i] XOR $a1[i])

#C = (C AND ($a0[i] XOR $a1[i])) OR ($a0[i] and $a1[i])
and $t5, $t1, $t2 #$t5 = $a0[i] AND $a1[i]
and $t6, $t3, $s1 #$t6 = C AND ($a0[i] XOR $a1[i])
or $s1, $t5, $t6  #C = (C AND ($a0[i] XOR $a1[i])) OR ($a0[i] and $a1[i])


#s0 = sum, $t0 = i, $t4 = Y, $t7 = mask register
insert_to_nth_bit($s0, $t0, $t4, $t7)  #S[i] = Y
add $t0, $t0, 1 # i++
beq $t0, 32, done_add_sub #if i = 32, we are done
j addition #goes back to addition to keep adding

done_add_sub:
move $v0, $s0 #Moves the sum ($s0) into $v0
move $v1, $s1 #Moves final carryout into $v1

restore_frame #Macro call to restore frame
##############################################################################################
#Procedure: twos_complement
#Usage: Computes the 2's complement of a number
#Arguments: $a0 - the number of which 2's complement is to be computed
#Return : $v0 - 2's complement of $a0
twos_complement:
store_frame #Macro call to store frame
#Continues to without resave
#Similar to twos complement but does not override the arguments (doesn't save), only used for negative
twos_complement_without_save:
not $a0, $a0 #$a0 = ~$a0
li $a1, 1    #$a1 = 1
jal add_logical #Computes ~$a0 + 1
restore_frame #Macro call to restore frame

#Procedure: twos_complement_if_neg
#Usage: Computes the 2's complement of a number only if it is negative
#Arguments: $a0 - the number of which 2's complement is to be computed (if negative)
#Return : $v0 - 2's complement of $a0
twos_complement_if_neg:
store_frame #Macro call to store
bltz $a0, twos_complement_without_save #If $a0 >= 0, we do not find its 2's complement
move $v0, $a0 #set $v0 to $a0 if $a0 is positive
restore_frame #Macro call to restore frame


#Procedure: twos_complement_64bit
#Usage: Computes the 2's complement of a 64 bit number
#Arguments: $a0 - the Lo of the number, $a1 - the Hi of the number
#Return: $v0 - Lo part of 2's complemented 64 bit, $v1 - Hi part of 2's complemented 64 bit
twos_complement_64bit:

store_frame #Macro call to store frame
not $a0, $a0 #$a0 = ~$a0
not $a1, $a1 #$a1 = ~$a1
move $s3, $a1	#Saves ~$a1 in $s3
li $a1, 1	#Loads $a1 with 1
jal add_logical	 #Calls add_logical to compute ~$a0 + 1
move $a1, $s3 	#Restores $a1 to its previous value
move $s3, $v0 #Stores sum in $s3
move $a0, $v1  #Sets $a0 to carry
jal add_logical #Adds carry to $a1
move $v1, $v0 #Sets $v1 (Hi) to (carry + $a1)
move $v0, $s3 #Restores $v0 (Lo)

restore_frame #Macro call to restore frame

#Procedure: bit_replicator
#Usage: Replicates a given bit 32 times
#Arguments: $a0 (either 0x0 or 0x1) - the bit to be replicated
#Return: $v0 (0x00000000 if $a0 = 0x0) or (0xFFFFFFFF if $a0 = 0x1)
bit_replicator:
store_frame #Macro call to store frame
beqz $a0, zero_rep #if $a0 = 0x0, we replicate 0x00000000 into $v0
li $v0, 0xFFFFFFFF #Otherwise we load $v0 with 0xFFFFFFFF
restore_frame

zero_rep:
li $v0, 0x00000000 #Stores 0x00000000 in $v0
restore_frame #Macro call to restore frame

############################################################################################################
#Procedure: mul_unsigned
#Usage: Computes $a0 x $a1
#Arguments: $a0 - multiplicand, $a1 - multiplier
#Return: Lo in $v0, Hi in $v1

mul_unsigned:
	li $s0, 0				# int i = 0
	li $s2, 0				# Sets Hi to 0
	move $s1, $a1				# Sets Lo to $a1 (multiplier)
	move $s3, $a0				# Saves the multiplicand for later use
mul_loop:
	extract_nth_bit($a0, $s1, $zero)	#Gets the LSB of the multiplier, sets it to $a0
	jal bit_replicator			#Replicates that bit 32 times, using utility procedure
	and $s4, $s3, $v0			#Performs an AND between the the multiplicand and the replicated bit
	move $a0, $s2				#Sets Hi to $a0 (we will call add_logical)
	move $a1, $s4				#Sets result of AND ($s4) to $a1 (we will call add_logical)
	jal add_logical				#Adds Hi and the first bit of the multiplicand ($s4)
	move $s2, $v0				#Updates the Hi to the result of the addition
	sra $s1, $s1, 1				#Shifts the multiplier ($s1) right by one
	extract_nth_bit($s5, $s2, $zero)	#Gets the LSB of the Hi, storing it in $s5
	li $s6, 31				#Sets $s6 (constant) to 31 for insert_to_nth_bit macro
	insert_to_nth_bit($s1, $s6, $s5, $t9)	#Sets MSB of the Lo to the LSB of the Hi
	sra $s2, $s2, 1				#Shifts the Hi right by 1
	add $s0, $s0, 1				#Increment counter (i++)
	li $s6, 32				#Updates the constant in $s6 to 32 for counter comparison
	beq $s0, $s6, mul_end			#If we are on our 32nd repetition (int i = 32) that means we are done
	j mul_loop				#Keeps looping otherwise

mul_end:
	move $v0, $s1				#Sets the Lo $v0
	move $v1, $s2				#Sets the Hi to $v1
	lw $a0, 52($sp)				#Restores the original multiplier from memory
	lw $a1, 48($sp)				#Restores the original multiplicand from memory
	li $s6, 31				#Sets $s6 to 31 to extract MSB
	extract_nth_bit($t7, $a0, $s6)		#Stores MSB of the original multiplier in $t7
	extract_nth_bit($t8, $a1, $s6)		#Stores MSB of the original multiplicand in $t8
	xor $t6, $t7, $t8			#Does an XOR to see whether the result should be negative or not (gives 0 if positive, 1 if negative)
	beqz $t6, return_mult			#If the result was 0 (positive), we are done
	move $a0, $v0				#Otherwise sets Lo to $v0 (for procedure call)
	move $a1, $v1				#Sets Hi to $v1 (for procedure call)
	jal twos_complement_64bit		#Does a two's complement of the 64 bit Hi & Lo registers
return_mult:
	j mul_done				#We are done
#############################################################################################################
#Procedure: div_unsigned
#Usage: Computes $a0/$a1
#Arguments: $a0 - dividend, $a1 - divisor
#Return: $v0 - quotient, $v1 - remainser
div_unsigned:
li $s0, 0 #int i = 0
move $s1, $a0 #Q($s1) = Dividend
move $s2, $a1 #D($s2) = Divisor
li $s3, 0     #R = 0

div_loop:
sll $s3, $s3, 1 #Shifts R to the left by 1 (R << 1)
li $s6, 31  	#Uses $s6 as a constant register to access MSB in macros
extract_nth_bit($t1, $s1, $s6) #Gets MSB of Q (Q[31])
insert_to_nth_bit($s3, $zero, $t1, $t9) #R[0] = Q[31]
sll $s1, $s1, 1	 #Shifts Q to the left by 1 (Q << 1)
move $a0, $s3 	#Sets $a0 to R for subtraction call
move $a1, $s2 	#Sets $a1 to divisor (D)
jal sub_logical	 #Calls sub_logical (computing S = R - D)
bltz $v0, increment_i
move $s3, $v0 #Sets remainder to result of subtraction
li $s6, 1	#Updates constant $s6 to extract LSB of quotient
insert_to_nth_bit($s1, $zero, $s6, $t9) #Inserts 1 into LSB of quotient

increment_i:
add $s0, $s0, 1	   #Increments i (i++)
beq $s0, 32, div_unsigned_end #If i == 32, we are done
j div_loop	#Otherwise keep looping

div_unsigned_end:
move $v0, $s1 #Moves quotient into $v0
move $v1, $s3 #Moves remainder into $v1
lw $a0, 52($sp) #Restores original $a0 from memory
lw $a1, 48($sp)	#Restores original $a1 from memory
li $s6, 31	#Sets constant register $s6 to 31 for MSB extraction
extract_nth_bit($t8, $a0, $s6)	#Gets MSB from dividend, stores it in $t8
extract_nth_bit($t9, $a1, $s6) #Gets MSB from divisor, stores it in $t9
xor $t6, $t8, $t9 #Does an xor to see if quotient is positive or not. XOR result of 0 signifies positive, 1 signifies negative
beqz $t6, rem_complement #If the quotient is positive, we don't complement it, goes to remainder instead
move $a0, $s1	#Sets $a0 to Q for complement procedure
jal twos_complement	#Procedure call
move $s1, $v0	#Updates quotient, now it is 2's complemented

rem_complement:
beqz $t8, skip_rem #If the remainder is 0, no need to complement
move $a0, $s3	#Sets $a0 to remainder for complement call
jal twos_complement
move $s3, $v0	#Updates remainder to its complemented form

skip_rem:
move $v0, $s1	#Sets $v0 to quotient ($s1)
move $v1, $s3	#Sets $v1 to remainder ($s3)
j div_done #We are done


##################################ALU RESTORE####################################
done:
restore_frame
