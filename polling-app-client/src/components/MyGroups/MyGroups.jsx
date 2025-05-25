import React, { Component } from "react";
import { ACCESS_TOKEN } from '../../constants';
import "./MyGroups.css";

class MyGroups extends Component {
  constructor(props) {
    super(props);
    this.state = {
      groups: [],
      loading: true,
      error: null,
    };
  }

  componentDidMount() {
    const token = localStorage.getItem(ACCESS_TOKEN);

    fetch("/api/groups/my", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error("그룹 목록을 불러오지 못했어요.");
        return res.json();
      })
      .then((data) => {
        this.setState({ groups: data, loading: false });
      })
      .catch((error) => {
        this.setState({ error: error.message, loading: false });
      });
  }

  render() {
    const { groups, loading, error } = this.state;

    return (
      <div className="my-groups-container">
        <h3>참여 중인 그룹</h3>
        {loading ? (
          <p>로딩 중...</p>
        ) : error ? (
          <p className="error">{error}</p>
        ) : groups.length === 0 ? (
          <p>참여한 그룹이 없습니다.</p>
        ) : (
          <div className="group-card-list">
            {groups.map((group) => (
              <div key={group.id} className="group-card">
                <div className="group-avatar" />
                <div className="group-info">
                  <div className="group-name">{group.name}</div>
                  <div className="group-count">멤버 {group.memberCount}명</div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    );
  }
}

export default MyGroups;
