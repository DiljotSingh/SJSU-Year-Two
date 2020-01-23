
# Add your macro definition here - do not touch cs47_common_macro.asm"
#<------------------------ MACRO DEFINITIONS ------------------------------------->#

#Macro: store_frame
#Usage: Call this to preserve frame during function calls
.macro store_frame		#Stores the frame (all argument and saved registers)
	addi	$sp, $sp, -60
	sw	$fp, 60($sp)
	sw	$ra, 56($sp)
	sw	$a0, 52($sp)
	sw	$a1, 48($sp)
	sw	$a2, 44($sp)
	sw	$a3, 40($sp)
	sw	$s0, 36($sp)
	sw	$s1, 32($sp)
	sw	$s2, 28($sp)
	sw	$s3, 24($sp)
	sw	$s4, 20($sp)
	sw	$s5, 16($sp)
	sw	$s6, 12($sp)
	sw	$s7, 8($sp)
	addi	$fp, $sp, 60
.end_macro

#Macro: restore_frame
#Usage: Call this after a function call has finished to restore all frames
.macro restore_frame		#Restores the frame
	lw	$fp, 60($sp)
	lw	$ra, 56($sp)
	lw	$a0, 52($sp)
	lw	$a1, 48($sp)
	lw	$a2, 44($sp)
	lw	$a3, 40($sp)
	lw	$s0, 36($sp)
	lw	$s1, 32($sp)
	lw	$s2, 28($sp)
	lw	$s3, 24($sp)
	lw	$s4, 20($sp)
	lw	$s5, 16($sp)
	lw	$s6, 12($sp)
	lw	$s7, 8($sp)
	addi	$sp, $sp, 60
	jr	$ra #Jumps back to return address
.end_macro

#Macro: extract_nth_bit
#Usage: Extracts the nth bit from a binary pattern
#Example: $t1 contains bit position, $s1 is the bit pattern; $t0 will be 0x0 or 0x1
.macro extract_nth_bit($regD, $regS, $regT)
	#$regD : will contain 0x0 or 0x1 depending on nth bit being 0 or 1
	#$regS: Source bit pattern
	#$regT: Bit position n (0-31)   
	move $s7, $regS		# Sets $t0 to the bit pattern ($regS) to avoid modifying original bit pattern
	srav $s7, $s7, $regT	# Shifts the bit pattern (in $t0) to the right by $regT (the position we are trying to compare $regD with)
	and $regD, $s7, 1	# By setting $regD to an 'AND' between $t0 and 1, $regD will be 1 if and only if $t0 was also 1. Otherwise, it will be 0. 
.end_macro

#Macro: insert_to_nth_bit
#Usage: Inserts a bit at nth bit to a bit pattern
#Example: $t1 contains 0x1, $s1 is bit position n, $t0 bit pattern will be inserted with 1 at nth position
.macro insert_to_nth_bit($regD, $regS, $regT, $maskReg)
	#$regD : This the bit pattern in which 1 to be inserted at nth position
	#$regS: Value n, from which position the bit to be inserted (0-31)
	#$regT: Register that contains 0x1 or 0x0 (bit value to insert)
	#$maskReg: Register to hold temporary mask

	li $maskReg, 1				# Intializes the mask register with 1; e.g. (M = 00000001)
	sllv $maskReg, $maskReg, $regS		# Shifts the mask register to the left by $regS (M << n)
	not $maskReg, $maskReg			# Inverts mask register (M = !M)
	and $regD, $regD, $maskReg		# Performs an 'AND' between the inverted mask register and $regD (the bit pattern)
	sllv $regT, $regT, $regS		# Shifts $regT (the bit value) to the left by $regS (b << n)
	or $regD, $regD, $regT			# Performs an 'OR' between $regD (the bit pattern) and $regT (the newly shifted bit value)
.end_macro
