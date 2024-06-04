## Bug Patrol - Streamlined Campus Transportation

Project Overview

Bug Patrol is a mobile application designed to revolutionize campus transportation by providing real-time buggy tracking, efficient ride-hailing services, and a seamless user experience. Firebase serves as the project's backbone for user authentication, data storage, and access control. Android Location Services are used to obtain driver location data, and the Android framework's built-in mapping capabilities (potentially leveraging Google Maps API) are used for map visualization within the app.

Key Features

* Real-time Buggy Tracking: Students can view the live location of buggies on an interactive map, empowering them to make informed decisions about their transportation needs.
* Efficient Ride-hailing: Students can conveniently request rides from nearby buggies, simplifying campus navigation and reducing wait times. Firebase Realtime Database manages ride requests, ensuring efficient ride allocation and tracking.
* Role-based Permissions: Buggy drivers log in and manage ride requests using a secure authentication system. Firebase's access control ensures only authorized drivers can accept or decline rides.
* In-App Notifications: Both students and drivers receive timely in-app alerts about ride requests, confirmations, and status updates. This fosters clear communication and enhances overall user experience.

Technical Stack

* Android Development Kit (SDK): Native Android development framework for building the app's core functionalities.
* Firebase: Cloud-based platform for user authentication, data storage (Realtime Database), and access control.
* Android Location Services: Used to obtain the driver's location data from the device's built-in GPS.
* Android Framework Maps (potentially using Google Maps API): Framework components used for displaying maps within the app. (Note: Verify if a third-party library is used instead)

Getting Started (for Developers)

1. Prerequisites:
    - Android Studio IDE ([https://developer.android.com/studio](https://developer.android.com/studio))
    - A Firebase project ([https://firebase.google.com/docs/projects/api/workflow_set-up-and-manage-project](https://firebase.google.com/docs/projects/api/workflow_set-up-and-manage-project))

2. Clone the Repository:
   ```bash
   git clone https://github.com/hemanthvarada/Bugpatrol.git

   ```

3. Set Up Firebase:
    - Follow Firebase documentation ([https://firebase.google.com/docs](https://firebase.google.com/docs)) to integrate Firebase Authentication, Realtime Database, and other relevant services into your project.
    - Replace placeholder values in the code with your Firebase project credentials.

How to Use the App (for End Users)

Students:

1. Download and install the Bug Patrol app from the Google Play Store (Note: App is not yet available on the Play Store as it's under development).
2. Create an account or log in using your existing credentials.
3. View the real-time location of buggies on the interactive map.
4. Request a ride from a nearby buggy.
5. Receive in-app notifications regarding the status of your ride request.

Buggy Drivers:

1. Download and install the Bug Patrol app.
2. Create an account or log in using your driver credentials.
3. View ride requests sent by students.
4. Accept or decline ride requests based on your availability.
5. Receive notifications about new ride requests.

Important Notes

* This README is intended for developers and provides a high-level overview. More detailed instructions and code documentation may be added in the future.

Contributing

We welcome contributions from the developer community! Feel free to fork the repository, make changes, and submit pull requests for review.

License

This project is licensed under the [MIT License](https://choosealicense.com/licenses/mit/).
