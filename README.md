# Park Easy
A simple parking services android app, which would provide you with an elegant way of reserving a parking spot almost anywhere in Macedonia.

# Installation Guide
Simply download the APK file to your phone and install it by going to your File Manager and clicking on the file name.

# Usage
If you are using Park Easy for the first time, then go ahead and sign up for free. If not, sign in and proceed with selecting a city, a date, a time slot and a parking
lot. Each parking lot keeps track of how many spots are available, and how many are occupied, at the given date and time. It is only logical that you cannot reserve a
spot if there are none left, and it is only fair that each user is eligible to having a maximum of 3 active reservations. Upon completing the reservation, you will be
given a QR code, which you can scan to get the location of the parking lot selected in your reservation. Or, you can simply click the navigation button which would give
you directions, using Google Maps. Upon arriving at your destination, delete your reservation.

# Architecture
# 1. Login/Register 
Sign up for free. Use the same credentials to sign in. 

# 2. Cities
Select a city for your reservation. Each city provided to you is part of a recycler view. Below the city name, there is also a number of available parking lots.

# 3. Reservation Form
Pick a date and a time slot for you reservation.

# 4. Parking Lots
Each parking lot is part of a recycler view and has a number of available and occupied spots, for the selected date and time slot, associated with it.
If the number of available spots for a given parking lot is 0, then choose another one. If there is literally no parking spot available, keep driving.

# 5. Reservation Confirmation
Once you've completed the reservation, you will be given a QR code, which you can scan to get the location of the selected parking lot. Or, you can simply click on the
navigation button and get directions right away, using Google Maps.

# 6. My Reservations
After you sign in, in the right corner of the toolbar, you will notice a small calendar-looking icon. By clicking it, you can review all your reservations (maximum 3).
You can either click the navigation button to get directions, or delete them, after you arrive at the destination.

# Thank you for using Park Easy and drive safe
