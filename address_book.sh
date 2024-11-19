#!/bin/bash
ADDRESS_BOOK="address_book.txt"

function create_address_book() {
    > $ADDRESS_BOOK
    echo "Address book created successfully."
}



function view_address_book() {
    if [ -f "$ADDRESS_BOOK" ]; then
        if [ -s "$ADDRESS_BOOK" ]; then
            cat $ADDRESS_BOOK
        else
            echo "Address book is empty."
        fi
    else
        echo "No address book found. Please create one first."
    fi

}



function insert_record() {
    read -p "Enter Name: " name
    read -p "Enter Address: " address
    read -p "Enter Phone Number: " phone
    read -p "Enter Email: " email

    echo "$name,$address,$phone,$email" >> $ADDRESS_BOOK

    echo "Record inserted successfully."
}



function delete_record() {
    read -p "Enter the Name of the record to delete: " name_to_delete
    if [ -f "$ADDRESS_BOOK" ]; then
        grep -v "^$name_to_delete," $ADDRESS_BOOK > temp.txt && mv temp.txt $ADDRESS_BOOK
        echo "Record '$name_to_delete' deleted successfully, if it existed."
    else
        echo "No address book found. Please create one first."
    fi
}



function modify_record() {
    read -p "Enter the Name of the record to modify: " name_to_modify
    if [ -f "$ADDRESS_BOOK" ]; then
        if grep -q "^$name_to_modify," $ADDRESS_BOOK; then
            grep -v "^$name_to_modify," $ADDRESS_BOOK > temp.txt
            read -p "Enter New Name: " name
            read -p "Enter New Address: " address
            read -p "Enter New Phone Number: " phone
            read -p "Enter New Email: " email

            echo "$name,$address,$phone,$email" >> temp.txt

            mv temp.txt $ADDRESS_BOOK
            echo "Record '$name_to_modify' modified successfully."
        else
            echo "Record '$name_to_modify' not found."
        fi
    else
        echo "No address book found. Please create one first."
    fi

}



function main_menu() {
    while true; do
        echo -e "\nAddress Book Menu:"
        echo "a) Create address book"
        echo "b) View address book"
        echo "c) Insert a record"
        echo "d) Delete a record"
        echo "e) Modify a record"
        echo "f) Exit"

        read -p "Select an option: " choice

        case $choice in
            a) create_address_book ;;
            b) view_address_book ;;
            c) insert_record ;;
            d) delete_record ;;
            e) modify_record ;;
            f) echo "Exiting..."; exit 0 ;;
            *) echo "Invalid choice. Please select again." ;;
        esac
    done
}

main_menu