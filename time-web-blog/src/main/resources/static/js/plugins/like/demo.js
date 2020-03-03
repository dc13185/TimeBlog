/**
 * demo.js
 * http://www.codrops.com
 *
 * Licensed under the MIT license.
 * http://www.opensource.org/licenses/mit-license.php
 *
 * Copyright 2016, Codrops
 * http://www.codrops.com
 */
;(function(window) {
	'use strict';
	let likeStatus = $("#articleId").attr("like-status");

	// taken from mo.js demos
	function isIOSSafari() {
		var userAgent;
		userAgent = window.navigator.userAgent;
		return userAgent.match(/iPad/i) || userAgent.match(/iPhone/i);
	};

	// taken from mo.js demos
	function isTouch() {
		var isIETouch;
		isIETouch = navigator.maxTouchPoints > 0 || navigator.msMaxTouchPoints > 0;
		return [].indexOf.call(window, 'ontouchstart') >= 0 || isIETouch;
	};

	// taken from mo.js demos
	var isIOS = isIOSSafari(),
		clickHandler = isIOS || isTouch() ? 'touchstart' : 'click';

	function extend( a, b ) {
		for( var key in b ) {
			if( b.hasOwnProperty( key ) ) {
				a[key] = b[key];
			}
		}
		return a;
	}

	function Animocon(el, options) {
		this.el = el;
		this.options = extend( {}, this.options );
		extend( this.options, options );


		if (likeStatus == 0){
			this.checked = false;
		}else {
			this.checked = true;
		}

		this.timeline = new mojs.Timeline();

		for(var i = 0, len = this.options.tweens.length; i < len; ++i) {
			this.timeline.add(this.options.tweens[i]);
		}

		var self = this;
		this.el.addEventListener(clickHandler, function() {
			if( self.checked ) {
				self.options.onUnCheck();
			}
			else {
				self.options.onCheck();
				self.timeline.start();
			}
			self.checked = !self.checked;
		});
	}

	Animocon.prototype.options = {
		tweens : [
			new mojs.Burst({
				shape : 'circle',
				isRunLess: true
			})
		],
		onCheck : function() { return false; },
		onUnCheck : function() { return false; }
	};

	// grid items:
	var items = [].slice.call(document.querySelectorAll('.grid__item'));


	function like(status) {
		let articleId = $("#articleId").val();
		let jsonContent = {"articleId":articleId,"status":status};
		$.ajax({
			url: ctx + "web/article/articleLike",
			type: 'post',
			data: JSON.stringify(jsonContent),
			processData: false,
			dataType: 'json',
			beforeSend: function (xhr) {
				xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
			}
		});

	}

	function init() {
		/* Icon 9 */
		/* Icon 14 */
		var el14 = items[0].querySelector('button.icobutton'), el14span = el14.querySelector('span'), el14counter = el14.querySelector('span.icobutton__text');
		if(likeStatus == 1){
			el14.style.color = '#F35186';
		}
		new Animocon(el14, {
			tweens : [
				// ring animation
				new mojs.Transit({
					parent: el14,
					duration: 750,
					type: 'circle',
					radius: {0: 40},
					fill: 'transparent',
					stroke: '#F35186',
					strokeWidth: {35:0},
					opacity: 0.2,
					x: '50%',
					y: '45%',
					isRunLess: true,
					easing: mojs.easing.bezier(0, 1, 0.5, 1)
				}),
				new mojs.Transit({
					parent: el14,
					duration: 500,
					delay: 100,
					type: 'circle',
					radius: {0: 20},
					fill: 'transparent',
					stroke: '#F35186',
					strokeWidth: {5:0},
					opacity: 0.2,
					x: '50%',
					y: '50%',
					shiftX : 40,
					shiftY : -60,
					isRunLess: true,
					easing: mojs.easing.sin.out
				}),
				new mojs.Transit({
					parent: el14,
					duration: 500,
					delay: 180,
					type: 'circle',
					radius: {0: 10},
					fill: 'transparent',
					stroke: '#F35186',
					strokeWidth: {5:0},
					opacity: 0.5,
					x: '50%',
					y: '50%',
					shiftX : -10,
					shiftY : -80,
					isRunLess: true,
					easing: mojs.easing.sin.out
				}),
				new mojs.Transit({
					parent: el14,
					duration: 800,
					delay: 240,
					type: 'circle',
					radius: {0: 20},
					fill: 'transparent',
					stroke: '#F35186',
					strokeWidth: {5:0},
					opacity: 0.3,
					x: '50%',
					y: '50%',
					shiftX : -70,
					shiftY : -10,
					isRunLess: true,
					easing: mojs.easing.sin.out
				}),
				new mojs.Transit({
					parent: el14,
					duration: 800,
					delay: 240,
					type: 'circle',
					radius: {0: 20},
					fill: 'transparent',
					stroke: '#F35186',
					strokeWidth: {5:0},
					opacity: 0.4,
					x: '50%',
					y: '50%',
					shiftX : 80,
					shiftY : -50,
					isRunLess: true,
					easing: mojs.easing.sin.out
				}),
				new mojs.Transit({
					parent: el14,
					duration: 1000,
					delay: 300,
					type: 'circle',
					radius: {0: 15},
					fill: 'transparent',
					stroke: '#F35186',
					strokeWidth: {5:0},
					opacity: 0.2,
					x: '50%',
					y: '50%',
					shiftX : 20,
					shiftY : -100,
					isRunLess: true,
					easing: mojs.easing.sin.out
				}),
				new mojs.Transit({
					parent: el14,
					duration: 600,
					delay: 330,
					type: 'circle',
					radius: {0: 25},
					fill: 'transparent',
					stroke: '#F35186',
					strokeWidth: {5:0},
					opacity: 0.4,
					x: '50%',
					y: '50%',
					shiftX : -40,
					shiftY : -90,
					isRunLess: true,
					easing: mojs.easing.sin.out
				}),
				// icon scale animation
				new mojs.Tween({
					duration : 1200,
					easing: mojs.easing.ease.out,
					onUpdate: function(progress) {
						if(progress > 0.3) {
							var elasticOutProgress = mojs.easing.elastic.out(1.43*progress-0.43);
							el14span.style.WebkitTransform = el14span.style.transform = 'scale3d(' + elasticOutProgress + ',' + elasticOutProgress + ',1)';
						}
						else {
							el14span.style.WebkitTransform = el14span.style.transform = 'scale3d(0,0,1)';
						}
					}
				})
			],
			onCheck : function() {
				el14.style.color = '#F35186';
				var current = Number(el14counter.innerHTML);
				el14counter.innerHTML = Number(el14counter.innerHTML) + 1;
				like();
			},
			onUnCheck : function() {
				el14.style.color = '#C0C1C3';
				var current = Number(el14counter.innerHTML);
				el14counter.innerHTML = current >= 1 ? Number(el14counter.innerHTML) - 1 : '';
				like();
			}
		});
	}

	init();

})(window);
