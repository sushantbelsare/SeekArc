# SeekArc
SeekArc : An Android Library For Circular SeekBar

SeekArc is an android UI library that can be used to show progress or modify it.

# Feel free to use it in your project!

Attribute | Description
---------- | ------------
startAngle | Defines the starting angle of arc eg. **app:startAngle = "270"**
sweepAngle | Defines the angle followed from starting poing eg. **app:sweepAngle = "90"** which will give arc with 270 starting poing and 270+90 end point
arcWidth | Defines the width of the arc eg. **app:arcWidth="5dp"**
arcColor | Defines the color of the arc eg. **app:arcColor="@android:color/darker_gray"**
progressColor | Defines the color of progress eg. **app:progressColor="@android:color/black"**
progress | Defines the value for progress eg. **app:progress="10"**
maxProgress | Defines the maximum limit eg. **app:maxProgress="100"**
thumbRadius | Defines the radius for thumb eg. **app:thumbRadius="5dp"**
thumbColor | Defines the color for thumb eg. **app:thumbColor="@android:color/blue"**. Use **app:thumbColor="@android:color/transparent"** to remove thumb
seekDirection | Defines the direction of progress eg. **app:seekDirection="clockwise"** or use **app:seekDirection="anticlockwise"** for anticlockwise direction.

---
**Gradle Dependency**

compile 'com.savantech.seekarc:seekarc:1.0'

---
**Maven Dependency**

< dependency >

  < groupId >com.savantech.seekarc< /groupId >
  
  < artifactId >seekarc< /artifactId >
  
  < version >1.0< /version >
  
  < type >pom< /type >
  
< /dependency >

---
