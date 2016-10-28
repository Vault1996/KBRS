function hashValue (a) {
    var elem = document.getElementById(a).value;
    document.getElementById(a).value = sha256(elem);
}