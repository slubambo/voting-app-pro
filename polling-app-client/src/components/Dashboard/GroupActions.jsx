import React, { Component } from 'react';
import GroupCreateModal from './GroupCreateModal';
import GroupJoinModal from './GroupJoinModal'; // ✅ 새로 import

class GroupActions extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showCreateModal: false,
      showJoinModal: false
    };
  }

  render() {
    return (
      <div className="group-actions-container">
        <div className="group-actions-text">
          <h3>+ 새로운 그룹 만들기</h3>
          <p>그룹을 만들어 보세요<br />의견, 일정을 한 곳에 모을 수 있어요</p>
        </div>
        <div className="group-actions-buttons">
          <button className="create-btn" onClick={() => this.setState({ showCreateModal: true })}>
            + 새 그룹 만들기
          </button>
          <button className="join-btn" onClick={() => this.setState({ showJoinModal: true })}>
            참여 코드로 그룹 입장하기
          </button>
        </div>

        <GroupCreateModal
          visible={this.state.showCreateModal}
          onClose={() => this.setState({ showCreateModal: false })}
          onCreated={this.props.onCreated}
        />
        <GroupJoinModal
          visible={this.state.showJoinModal}
          onClose={() => this.setState({ showJoinModal: false })}
          onJoined={this.props.onCreated}
        />
      </div>
    );
  }
}

export default GroupActions;
