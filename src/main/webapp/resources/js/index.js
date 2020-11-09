
//************************* intro clock **************************

// clock digits
let cdigits = $('.cdigit').toArray();

const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

// refreshes time on the clock
function refresh$time() {
    // building objects of class Date for getting time
    let date = new Date();

    let hours = date.getHours();
    let minutes = date.getMinutes();
    let seconds = date.getSeconds();
    // digits of hours
    cdigits[0].innerHTML = ((hours-hours%10)/10).toString();
    cdigits[1].innerHTML = (hours%10).toString();
    //digits of minutes
    cdigits[2].innerHTML = ((minutes-minutes%10)/10).toString();
    cdigits[3].innerHTML = (minutes%10).toString();
    // digits of seconds
    cdigits[4].innerHTML = ((seconds-seconds%10)/10).toString();
    cdigits[5].innerHTML = (seconds%10).toString();

    $('#day').html(date.getDate());
    $('#month').html(months[date.getMonth()]);
    $('#year').html(date.getFullYear());
}

// notifies the method refresh$clock() in every second
$(function () {
    setInterval(refresh$time, 1000);
    refresh$time(); // first running
})
