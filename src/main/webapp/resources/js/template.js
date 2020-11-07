
//************************* sidebar clock **************************

// clock digits
let digits = $('.digit').toArray();

// refreshes time on the clock
function refresh$clock() {
    // building objects of class Date for getting time
    let date = new Date();

    let hours = date.getHours();
    let minutes = date.getMinutes();
    let seconds = date.getSeconds();
    // digits of hours
    digits[0].innerHTML = ((hours-hours%10)/10).toString();
    digits[1].innerHTML = (hours%10).toString();
    //digits of minutes
    digits[2].innerHTML = ((minutes-minutes%10)/10).toString();
    digits[3].innerHTML = (minutes%10).toString();
    // digits of seconds
    digits[4].innerHTML = ((seconds-seconds%10)/10).toString();
    digits[5].innerHTML = (seconds%10).toString();
}

// notifies the method refresh$clock() in every second
$(function () {
    refresh$clock(); // first running
    setInterval(refresh$clock, 1000);
})

//*********************** sidebar scrolling ************************

// sidebar
let sidebar = $('.sidebar');

// scrolls the sidebar when scrolls the window
$(window).scroll(function () {
    let length = window.pageYOffset - sidebar.scrollTop();
    sidebar.css('padding-top', length + 'px')
});
