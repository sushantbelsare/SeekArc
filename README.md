# SeekArc
SeekArc : An Android Library For Circular SeekBar

SeekArc is an android UI library that can be used to show progress or modify it.

---

**Gradle Dependency**

compile 'com.savantech.seekarc:seekarc:1.0.2'

---
**Maven Dependency**

< dependency >

  < groupId >com.savantech.seekarc< /groupId >
  
  < artifactId >seekarc< /artifactId >
  
  < version >1.0.2< /version >
  
  < type >pom< /type >
  
< /dependency >

---

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
roundCorners | Clips corners in round shape eg. **app:roundCorners="true"**

---
Method | Description
------ | -----------
setStartAngle(int startAngle) | Sets starting angle eg. **seekArc.setStartAngle(270)**
getStartAngle() | Returns start angle of type integer
setSweepAngle(int sweepAngle) | Sets sweep angle eg. **seekArc.setSweepAngle(90)**
getSweepAngle() | Returns sweep angle of type integer
setArcWidth(int arcWidth) | Sets arc width eg. **seekArc.setArcWidth(15)**
getArcWidth() | Returns arc width of type integer
setArcColor(int arcColor) | Sets arc color eg. **seekArc.setArcColor(Color.DK_GRAY)**
setProgress(float progress) | Sets progress eg. **seekArc.setProgress(10f)**
getProgress() | Returns progress of type float
setProgressColor(int progressColor) | Sets progress color eg. **seekArc.setProgressColor(Color.LT_GRAY)**
setMaxProgress(float maxProgress) | Sets maximum limit eg. **seekArc.setMaxProgress(100f)**
setThumbRadius(int thumbRadius) | Sets radius for thumb eg. **seekArc.setThumbRadius(5)**
setThumbColor(int thumbColor) | Sets color for thumb eg. **seekArc.setThumbColor(Color.WHITE)**
setSeekDirection(String direction) | Sets direction for progress eg. **seekArc.setSeekDirection(seekArc.CLOCKWISE)** or **seekArc.setSeekDirection(seekArc.ANTICLOCKWISE)**
setRoundCorners(boolean roundCorners) | Sets round corners eg. **seekArc.setRoundCorners(true)**
---

