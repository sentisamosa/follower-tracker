(this["webpackJsonpfollower-tracker"]=this["webpackJsonpfollower-tracker"]||[]).push([[0],{20:function(e,a,t){e.exports=t(30)},30:function(e,a,t){"use strict";t.r(a);var n=t(0),r=t.n(n),l=t(18),c=t.n(l),o=t(6),m=t(7),i=t(9),s=t(8),u=t(11),h=function(e){var a=e.dark,t=e.children,n=e.className,l=e.pages;return a=a?"dark":"light",r.a.createElement("nav",{className:"Header navbar navbar-".concat(a," bg-").concat(a)+(n?" "+n:"")+(l&&l.length>0?" navbar-expand-lg":"")},r.a.createElement("span",{className:"navbar-brand"},t),l&&l.length>0&&r.a.createElement("div",{className:"collapse navbar-collapse"},r.a.createElement("ul",{className:"navbar-nav mr-auto"},l.map((function(e,a){return r.a.createElement("li",{className:"nav-item",key:a},r.a.createElement(u.b,{to:e.Path,className:"nav-link"},e.Name))})))))},d=t(1),v=function(e){Object(i.a)(t,e);var a=Object(s.a)(t);function t(){var e;Object(o.a)(this,t);for(var n=arguments.length,r=new Array(n),l=0;l<n;l++)r[l]=arguments[l];return(e=a.call.apply(a,[this].concat(r))).state={FollowingData:[]},e}return Object(m.a)(t,[{key:"componentDidMount",value:function(){var e,a=this;(e="kshtj24",fetch("https://api.github.com/users/".concat(e,"/following")).then((function(e){return e.json()}))).then((function(e){return a.setState({FollowingData:e})}))}},{key:"render",value:function(){return r.a.createElement("div",{className:"row"},this.state.FollowingData.map((function(e,a){return r.a.createElement("div",{className:"col-sm-4 my-3",key:a},r.a.createElement("div",{className:"card"},r.a.createElement("div",{className:"card-header"},e.login),r.a.createElement("div",{className:"card-body"},r.a.createElement("div",{className:"card-text"},r.a.createElement("img",{src:e.avatar_url,className:"mg-thumbnail img-fluid",alt:e.key}),r.a.createElement("p",null,e.id),r.a.createElement("a",{href:e.html_url,className:"btn btn-primary"},"View")))))})))}}]),t}(n.Component),f=function(e){Object(i.a)(t,e);var a=Object(s.a)(t);function t(){var e;Object(o.a)(this,t);for(var n=arguments.length,r=new Array(n),l=0;l<n;l++)r[l]=arguments[l];return(e=a.call.apply(a,[this].concat(r))).state={FollowerData:[]},e}return Object(m.a)(t,[{key:"componentDidMount",value:function(){var e,a=this;(e="kshtj24",fetch("https://api.github.com/users/".concat(e,"/followers")).then((function(e){return e.json()}))).then((function(e){return a.setState({FollowerData:e})}))}},{key:"render",value:function(){return r.a.createElement("div",{className:"row"},this.state.FollowerData.map((function(e,a){return r.a.createElement("div",{className:"col-sm-4 my-3",key:a},r.a.createElement("div",{className:"card"},r.a.createElement("div",{className:"card-header"},e.login),r.a.createElement("div",{className:"card-body"},r.a.createElement("div",{className:"card-text"},r.a.createElement("img",{src:e.avatar_url,alt:e.key,className:"img-thumbnail img-fluid"}),r.a.createElement("p",null,e.id),r.a.createElement("a",{href:e.html_url,className:"btn btn-outline-primary",role:"button"},"View"),r.a.createElement("a",{href:"followme#",className:"btn btn-outline-primary ml-1",role:"button"},"Follow")))))})))}}]),t}(n.Component),b=function(e){Object(i.a)(t,e);var a=Object(s.a)(t);function t(){return Object(o.a)(this,t),a.apply(this,arguments)}return Object(m.a)(t,[{key:"render",value:function(){return r.a.createElement("div",null)}}]),t}(n.Component),E=function(){return r.a.createElement("div",null)},p=[{Name:"Login",Path:"/login",PageItem:r.a.createElement(E,null)},{Name:"Following",Path:"/ifollow",PageItem:r.a.createElement(v,null)},{Name:"Follower",Path:"/followme",PageItem:r.a.createElement(f,null)},{Name:"Who Don't Follow",Path:"/notfollowingback",PageItem:r.a.createElement(b,null)}],g=function(e){Object(i.a)(t,e);var a=Object(s.a)(t);function t(){return Object(o.a)(this,t),a.apply(this,arguments)}return Object(m.a)(t,[{key:"render",value:function(){return r.a.createElement("div",null,r.a.createElement(h,{dark:!0,pages:p},"Follower Tracker"),r.a.createElement(d.c,null,p.map((function(e,a){return r.a.createElement(d.a,{path:e.Path,key:a},r.a.createElement("div",{className:"container"},e.PageItem))})),r.a.createElement(d.a,null,r.a.createElement("h3",{className:"bg-danger"},"Error 403"))))}}]),t}(n.Component);Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));c.a.render(r.a.createElement(r.a.StrictMode,null,r.a.createElement(u.a,null,r.a.createElement(g,null))),document.getElementById("root")),"serviceWorker"in navigator&&navigator.serviceWorker.ready.then((function(e){e.unregister()})).catch((function(e){console.error(e.message)}))}},[[20,1,2]]]);
//# sourceMappingURL=main.b52377b1.chunk.js.map