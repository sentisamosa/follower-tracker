import React, { Component } from "react";
import { GetUserFollowing } from "../services/GitHubService";

class Following extends Component {
  state = {
    FollowingData: []
  };
  componentDidMount() {
    GetUserFollowing("kshtj24").then(res =>
      this.setState({
        FollowingData: res
      })
    );
  }

  render() {
    return (
      <div className="row">
        {this.state.FollowingData.map((item, key) => (
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
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    );
  }
}

export default Following;
