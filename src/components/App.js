import React, { Component } from "react";
import Header from "./shared/Header";
import { Switch, Route } from "react-router-dom";
import Following from "./Following";
import Follower from "./Follower";

const pages = [
  { Name: "Following", Path: "/ifollow" },
  { Name: "Following", Path: "/followme" },
  { Name: "Who Don't Follow", Path: "/delta" }
];

class App extends Component {
  render() {
    return (
      <div>
        <Header dark={false} pages={pages}>
          Follower Tracker
        </Header>
        <Switch>
          <Route path="/ifollow">
            <Following />
          </Route>
          <Route path="/followme">
            <Follower />
          </Route>
          <Route path="/delta"></Route>
        </Switch>
      </div>
    );
  }
}

export default App;
