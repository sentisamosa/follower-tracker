import React, { Component } from "react";
import Header from "./shared/Header";
import { Switch, Route } from "react-router-dom";
import Following from "./Following";
import Follower from "./Follower";

const pages = [
  { Name: "Following", Path: "/ifollow" },
  { Name: "Follower", Path: "/followme" },
  { Name: "Who Don't Follow", Path: "/notfollowingback" }
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
            <Route path={item.Path}>
              <h3>{item.Name}</h3>
              <p>This is the {item.Name} page</p>
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
