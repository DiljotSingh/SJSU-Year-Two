.text
#-------------------------------------------
# Procedure: insertion_sort
# Argument: 
#	$a0: Base address of the array
#       $a1: Number of array element
# Return:
#       None
# Notes: Implement insertion sort, base array 
#        at $a0 will be sorted after the routine
#	 is done.
#-------------------------------------------
insertion_sort:
	# Caller RTE store (TBD)
	addi $sp, $sp, -20
	sw $fp, 20($sp)
	sw $ra, 16($sp)
	sw $a0, 12($sp)
	sw $a1, 8 ($sp)
	addi $fp, $sp, 20
	
		# Implement insertion sort (TBD)
		addi $t0, $zero, 1 #int i($t0) = 1
for_loop:	beq $t0, $a1, end_for #for int i = 1, to length of Array ($a1)
			la $t1, ($t0) #int j ($t1) = i ($t0)
	while_loop:	blez $t1, end_while #while j > 0
			sll $t6, $t1, 2 # offset_J ($t6) = j * 4
			add $t2, $a0, $t6 #$t2 is now the address of Arr[j]
			lw $t3, 0($t2) #t3 is equal to the contents of Arr[j]
			lw $t4, -4($t2) #t4 is equal to the contents of Arr[j-1]
			ble $t4, $t3, end_while #while Arr[j-1] > Arr[j]
			#swap Arr[j] and Arr[j-1]
			la $t5, ($t4) #int temp = Arr[j-1]
			sw $t3, -4($t2) # Arr[j-1] = Arr[j]
			sw $t5, ($t2)  #Arr[j] = temp
			addi $t1, $t1, -1 # j = j - 1
			j while_loop #Goes back to while loop
	end_while: addi $t0, $t0, 1 #i = i + 1
		   j for_loop 
		
end_for: j insertion_sort_end #Done
	
	
insertion_sort_end:
	# Caller RTE restore (TBD)
	lw $fp, 20($sp)
	lw $ra, 16($sp)
	lw $a0, 12($sp)
	lw $a1, 8 ($sp)
	addi $sp, $sp, 20
	# Return to Caller
	jr	$ra
