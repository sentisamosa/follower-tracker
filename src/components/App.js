import React, { Component } from "react";
import Header from "./shared/Header";
import { Switch, Route } from "react-router-dom";
import Following from "./Following";
import Follower from "./Follower";
import NotFollowing from "./NotFollowing";
import Login from "./Login";

const pages = [
  {
    Name: "Login",
    Path: "/",
    PageItem: <Login />,
    Exact: true
  },
  {
    Name: "Following",
    Path: "/ifollow",
    PageItem: <Following />,
    Exact: true
  },
  {
    Name: "Follower",
    Path: "/followme",
    PageItem: <Follower />,
    Exact: true
  },
  {
    Name: "Who Don't Follow",
    Path: "/notfollowingback",
    PageItem: <NotFollowing />,
    Exact: true
  }
];

class App extends Component {
  render() {
    return (
      <div>
        <Header dark={true} pages={pages}>
          Follower Tracker
        </Header>
        <Switch>
          {pages.map((item, key) => (
            <Route path={item.Path} exact={item.Exact} key={key}>
              <div className="container">{item.PageItem}</div>
            </Route>
          ))}
          <Route>
            <h3 className="bg-danger">Error 403</h3>
          </Route>
        </Switch>
      </div>
    );
  }
}

export default App;
