# Travel-Planner-Vaadin-FrontEnd is vacation planner
!! This is just to represent the functionality of the backend!!


#### Running from vps.com:
- 1.First clone this fron-end repo and run.
- 2.Go to the following URL: http://localhost:8081/maninView.

![1.png](src%2Fmain%2Fresources%2Fimages%2F1.png)
- 3.`Get Strat` button will redirect you to menu

![2.png](src%2Fmain%2Fresources%2Fimages%2F2.png)

#### Running locally:
- 1.First run back-end-> [Back-End](https://github.com/domKul/Travel-Planner).
- 2.Then run front.
 
### Instruction

- 1.`Menage travelers` button 
![18.png](src%2Fmain%2Fresources%2Fimages%2F18.png)
In this page you can create, find, edit or delete traveler information, you cant also add complaints.


Example `Create traveler`:
![4.png](src%2Fmain%2Fresources%2Fimages%2F4.png)

After creating traveler you can find information about him in the list, now you can do some operation on selected traveler like add complaint, delete or edit.
![5.png](src%2Fmain%2Fresources%2Fimages%2F5.png)

`Complaints`-> `add` button
![6.png](src%2Fmain%2Fresources%2Fimages%2F6.png)
You need pass the customer ID (traveler id), when you create some complaint you can refresh the list and show all complaints added.
![7.png](src%2Fmain%2Fresources%2Fimages%2F7.png)


- 2.`Find destination`-> `Search Location` button
![8.png](src%2Fmain%2Fresources%2Fimages%2F8.png)
![9.png](src%2Fmain%2Fresources%2Fimages%2F9.png)


After giving the `Name` and `Locale` you will find all locations with given `Name`
![10.png](src%2Fmain%2Fresources%2Fimages%2F10.png)
After doing that you can find destination.

- 2.`Find destination`-> `Search Destination` button
![11.png](src%2Fmain%2Fresources%2Fimages%2F11.png)

Pss all information `dest_id` and `destination_type` must be correctly copied from Locations like this
![12.png](src%2Fmain%2Fresources%2Fimages%2F12.png)

When you got your destination list you can select one position and click `Find` button to search more information about it will open google search with the selected destination
![13.png](src%2Fmain%2Fresources%2Fimages%2F13.png)

`Add Booking` button
When you choose your destination you can create a booking

![14.png](src%2Fmain%2Fresources%2Fimages%2F14.png)
Select Start End date, the customer id(traveler id) and destination id.When you add a booking successfully you will receive an email to the
address of selected traveler with information about thr booking

![15.png](src%2Fmain%2Fresources%2Fimages%2F15.png)

- 3.`Booking plan` button
![16.png](src%2Fmain%2Fresources%2Fimages%2F16.png)

All your bookings are here you can add new and delete when you click on one of booking plan you will see full information about

![17.png](src%2Fmain%2Fresources%2Fimages%2F17.png)
###### There is a chance that an application will not work if it is hosted on a vps server.(the localhost version work in 100%)
###### This project was build with:
###### java 17
###### Spring version 3.0.6
###### Vaadin version 24.0.5

 
