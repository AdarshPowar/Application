package com.techad.application

class User {
    var name: String? = null
    var email: String? = null
    var uid: String? = null

    constructor() {}  // Required for Firebase

    constructor(name: String, email: String, uid: String) {
        this.name = name
        this.email = email
        this.uid = uid
    }
}
