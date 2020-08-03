import React, { Component } from "react";
import { GetUserFollowers } from "../services/GitHubService";
class Follower extends Component {
  state = {
    FollowerData: []
  };

  componentDidMount() {
    GetUserFollowers("kshtj24").then(res =>
      this.setState({
        FollowerData: res
      })
    );
  }

  render() {
    return (
      <div className="row">
        {this.state.FollowerData.map((item, key) => (
          <div class="col-sm-4 my-3">
            <div className="card">
              <div className="card-header">{item.login}</div>
              <div className="card-body">
                <div className="card-text">
                  <p>{item.id}</p>
                  <p>{item.type}</p>
                  <a href={item.html_url} className="btn btn-primary">
                    View
                  </a>
                  <a href={item.html_url} className="btn btn-primary">
                    Follow
                  </a>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    );
  }
}

export default Follower;
