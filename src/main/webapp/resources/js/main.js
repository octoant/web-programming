
//**************************** checkbox ****************************

// checkbox list
let checkboxes = $('.checkbox').toArray();

// selects last selected checkbox | unselects it
function f(index) {
    for (checkbox of checkboxes) {
        if (checkbox !== checkboxes[index-1]) {
            checkbox.checked = false;
        }
    }
    foo$lines();
}

// returns number of checked checkbox
function get$r() {
    for (let i = 0; i < checkboxes.length; i ++) {
        if (checkboxes[i].checked) return i+1;
    }
}

//************************ input validations ***********************

// input text
let input = $('#input');

let tmp;
// validation input field
$(input).bind('input load', function() {
    let sign;
    if (this.value.length > 0) {
        sign = this.value.charAt(0);
    }
    sign = (sign === '-') ? sign : "";
    // replaces invalid chars to ''
    this.value = sign + this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
    foo$lines();
});

//************************ graphic listeners ***********************

// graphic
let graphic = $('#graph');

// notifies when a mouse on the graph
$(graphic).mousemove(function(event) {
    let handler = get$handler(event);
    redraw$lines(handler.x, handler.y);
});

// notifies when a mouse leaves the graph
$(graphic).mouseleave(function () {
    foo$lines();
});

// ace slider entry
let slider$entry = ice.ace.instance('j_idt21');

// notifies when clicks the graph
$(graphic).click(function(event) {
    let handler = get$handler(event);

    if (get$r() !== undefined) {
        // setting value for slider entry
        slider$entry.setValue(handler.x);
        slider$entry.input[0].value = handler.x;
        // setting value for input text
        $('#input').val(handler.y);
        $('#submit').click();
        // redrawing the point
        redraw$lines(handler.x, handler.y);
        redraw$point(handler.x, handler.y);
    } else {
        $('#submit-msg').html('Please select value of R')
    }
});

//************************ form listener ***************************

$(foo$lines());
function foo$lines() {
    let float$value = parseFloat($(input).val());
    // y messages
    if (!isNaN(float$value) && get$r() !== undefined) {
        redraw$lines(slider$entry.getValue(), float$value);
    } else {
        setTimeout(() => {
            $('#line1').attr('stroke-width', 0);
            $('#line2').attr('stroke-width', 0);
        }, 500);
    }
}

//*********************** submit listener **************************

$('#submit').click(function() {
    $('#submit-msg').html(submit$action());
})

// checks and returns submit message
function submit$action() {
    let float$value = parseFloat($(input).val());
    // y messages
    if (!isNaN(float$value)) {
        if (float$value < -5.0) {
            return 'Value of the Y must be greater than -5';
        }
        if (float$value > 3.0) {
            return 'Value of the Y must be less than 3';
        }
    } else {
        return 'Please enter a valid Y';
    }
    // r messages
    if (get$r() === undefined) {
        return 'Please select value of R';
    }
    redraw$point(slider$entry.getValue(), float$value);
    redraw$lines(slider$entry.getValue(), float$value);
    return '';
}

//******************* handles graph coordinates ********************

// returns a true coordinates moved on the graph
function get$handler(event) {
    let rect = graphic.get(0).getBoundingClientRect();

    // parsing event coordinates relatively to the graphs
    let pixel$x = (event.clientX - rect.x) * 400 / rect.width;
    let pixel$y = (event.clientY - rect.y) * 400 / rect.height;

    // radius equal to two(2) by default
    let r = (get$r() === undefined) ? 2 : get$r();

    // parsing to the number
    let x = (fix$number(pixel$x, 30, 370) - 200) * r / 160;
    let y = - (fix$number(pixel$y, 30, 370) - 200) * r / 160;

    return {
        x: fix$number(Math.round(x), -5, 5),
        y: fix$number(y, -5, 3)
    };
}

// returns fixed number from sides (min | max)
function fix$number(number, min, max) {
    if (number < min) return min;
    if (number > max) return max;
    return number;
}

//**************************** redrawing ***************************

// redraws the point coordinate's lines
function redraw$lines(x, y) {
    let r = (get$r() === undefined) ? 2 : get$r();

    let relative$x = 160 * x / r + 200;
    let relative$y = - 160 * y / r + 200;

    $('#line1')
        .attr('stroke-width', 1.2)
        .attr('y1', relative$y)
        .attr('y2', relative$y);

    $('#line2')
        .attr('stroke-width', 1.2)
        .attr('x1', relative$x)
        .attr('x2', relative$x);

}

// redraws the point on the graph
function redraw$point(x, y) {
    let r = (get$r() === undefined) ? 2 : get$r();

    let relative$x = 160 * x / r + 200;
    let relative$y = - 160 * y / r + 200;

    $('#point')
        .attr('r', 3)
        .attr('cx', relative$x)
        .attr('cy', relative$y);
}
