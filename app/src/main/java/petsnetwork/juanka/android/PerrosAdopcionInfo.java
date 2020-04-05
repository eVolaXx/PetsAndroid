package petsnetwork.juanka.android;

public class PerrosAdopcionInfo {

    public String name, user, time, dog_image, date, race, uid, age, description, comportamiento, contact;

    public PerrosAdopcionInfo() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public String getDogImage() {
        return dog_image;
    }

    public void setDogImage(String dog_image) {
        this.dog_image = dog_image;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public PerrosAdopcionInfo(String name, String dog_image, String time, String date, String user, String uid, String race, String age, String description, String comportamiento, String contact) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.dog_image = dog_image;
        this.uid = uid;
        this.user = user;
        this.race = race;
        this.age = age;
        this.dog_image = dog_image;
        this.description = description;
        this.comportamiento = comportamiento;
        this.contact = contact;

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDogName() {
        return name;
    }

    public void setDog_name(String name) {
        this.name = name;
    }

    public String getEdad() {
        return age;
    }

    public void setEdad(String age) {
        this.age = age;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComportamiento() {
        return comportamiento;
    }

    public void setComportamiento(String comportamiento) {
        this.comportamiento = comportamiento;
    }

    public String getContacto() {
        return contact;
    }

    public void setContacto(String contact) {
        this.contact = contact;
    }


}
