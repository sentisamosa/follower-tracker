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
          <div className="col-sm-4 my-3" key={key}>
            <div className="card">
              <div className="card-header">{item.login}</div>
              <div className="card-body">
                <div className="card-text">
                  <img
                    src={item.avatar_url}
                    alt={item.key}
                    className="img-thumbnail img-fluid"
                  />
                  <p>{item.id}</p>
                  <a
                    href={item.html_url}
                    className="btn btn-outline-primary"
                    role="button"
                  >
                    View
                  </a>
                  <a
                    href="followme#"
                    className="btn btn-outline-primary ml-1"
                    role="button"
                  >
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
