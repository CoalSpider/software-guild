var canvas;
var gc;
const twoPI = Math.PI * 2;

const activeRings = [];

var Ring = function (clickX, clickY, radius) {
    this.x = clickX;
    this.y = clickY;
    this.radius = radius;
    this.gradient = gc.createRadialGradient(clickX, clickY, 0, clickX, clickY, 400);
    this.gradient.addColorStop("0", "grey");
    this.gradient.addColorStop("0.25", "white");
}
Ring.prototype.step = function () {
    this.radius += 4;
    if (this.radius == 6 * 4 + 1 || this.radius == 12 * 4 + 1) {
        var r = new Ring(this.x, this.y, 1);
        r.step = function () {
            this.radius += 4;
        };
        activeRings.push(r);
    }
}
$(document).ready(function () {
    $("body").css('cursor', 'url(secretImages/doritoCursor2.png),auto');
    $("button").css('cursor', 'url(secretImages/doritoCursor2.png),auto');
    $("input").css('cursor', 'url(secretImages/doritoCursor2.png),auto');

    $("body").prepend('<canvas id="paper" width="' + $(window).width() + '" height="' + $(window).height() + '"style="position:fixed; top:0;left:0; z-index: -1;"></canvas>');
    canvas = $("#paper")[0];
    gc = canvas.getContext('2d');
    $(window).click(function (e) {
        var relativeX = (e.pageX - $("#paper").offset().left),
            relativeY = (e.pageY - $("#paper").offset().top);
        activeRings.push(new Ring(relativeX, relativeY, 1));
    });
    requestAnimationFrame(step);
});
var num = 0;
function update(timestamp) {
    for (var i = 0; i < activeRings.length; i++) {
        activeRings[i].step();
        if (activeRings[i].radius > 100) {
            activeRings.splice(i, 1);
        }
    }
    if (++num > 10) {
        activeRings.push(new Ring(Math.random() * canvas.width, Math.random() * canvas.height, 1));
        num = 0;
    }
}
function draw() {
    gc.fillStyle = "white";
    gc.fillRect(0, 0, canvas.width, canvas.height);
    gc.lineWidth = 5;
    $.each(activeRings, function (index, ring) {
        gc.strokeStyle = ring.gradient;
        gc.beginPath();
        gc.arc(ring.x, ring.y, ring.radius, 0, twoPI, false);
        gc.stroke();
    });
}
function step(timestamp) {
    update(timestamp);
    draw();
    requestAnimationFrame(step);
}